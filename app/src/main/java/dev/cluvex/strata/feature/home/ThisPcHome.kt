package dev.cluvex.strata.feature.home

import android.os.Environment
import android.os.StatFs
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SdCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.cluvex.strata.R
import dev.cluvex.strata.core.remote.RemoteConnection
import dev.cluvex.strata.ui.theme.Win11
import java.io.File
import java.util.Locale

private class DriveEntry(val name: String, val root: File, val icon: ImageVector)

private fun storageInfo(f: File): Pair<Long, Long> = try {
    val s = StatFs(f.path)
    s.availableBytes to s.totalBytes
} catch (e: Exception) {
    0L to 0L
}

private fun size(b: Long): String {
    if (b < 1024) return "$b B"
    val units = arrayOf("KB", "MB", "GB", "TB")
    var v = b.toDouble()
    var i = -1
    while (v >= 1024 && i < 3) { v /= 1024; i++ }
    return String.format(Locale.US, "%.1f %s", v, units[i])
}

/**
 * Windows 11 "This PC" start page: address bar, Folders, Devices and drives (with usage bars).
 * Shown when the app starts and from the drawer's "This PC" entry.
 */
@Composable
fun ThisPcHome(
    root: String,
    storages: List<RemoteConnection>,
    onOpenPath: (String) -> Unit,
    onOpenStorage: (RemoteConnection) -> Unit,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val drives = remember {
        val list = mutableListOf(DriveEntry("Internal Storage", Environment.getExternalStorageDirectory(), Icons.Filled.PhoneAndroid))
        context.getExternalFilesDirs(null).drop(1).filterNotNull().forEach {
            val r = File(it.path.substringBefore("/Android"))
            if (r.canRead()) list.add(DriveEntry("SD Card", r, Icons.Filled.SdCard))
        }
        list
    }
    val folders = remember(root) {
        listOf("Download", "Documents", "Pictures", "Music", "Movies", "DCIM")
            .map { File(root, it) }.filter { it.isDirectory }
    }
    val colors = MaterialTheme.colorScheme

    Column(modifier.fillMaxSize().background(colors.background).statusBarsPadding()) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onOpenDrawer) { Icon(Icons.Filled.Menu, null) }
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = colors.surfaceContainerHigh,
                border = BorderStroke(1.dp, colors.outlineVariant),
                modifier = Modifier.weight(1f).height(40.dp),
            ) {
                Row(Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Computer, null, tint = colors.primary, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("This PC", fontSize = 14.sp, color = colors.onSurface)
                }
            }
        }

        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp)) {
            Section(R.string.thispc_folders)
            folders.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                    row.forEach { f ->
                        Column(
                            Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).clickable { onOpenPath(f.path) }.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(Icons.Filled.Folder, null, tint = Win11.FolderYellow, modifier = Modifier.size(52.dp))
                            Text(f.name, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, color = colors.onSurface)
                        }
                    }
                    repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                }
            }

            Spacer(Modifier.height(18.dp))
            Section(R.string.thispc_drives)
            drives.forEach { d ->
                val (free, total) = remember(d.root) { storageInfo(d.root) }
                val used = if (total > 0) (total - free).toFloat() / total else 0f
                Card(Modifier.clickable { onOpenPath(d.root.path) }) {
                    Icon(d.icon, null, tint = colors.primary, modifier = Modifier.size(44.dp))
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(d.name, fontSize = 15.sp, color = colors.onSurface)
                        Spacer(Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { used },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = if (used > 0.9f) Win11.DriveBarRed else colors.primary,
                            trackColor = colors.surfaceVariant,
                            gapSize = 0.dp,
                            drawStopIndicator = {},
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("${size(free)} free of ${size(total)}", fontSize = 12.sp, color = colors.onSurfaceVariant)
                    }
                }
            }

            if (storages.isNotEmpty()) {
                Spacer(Modifier.height(18.dp))
                Section(R.string.thispc_network)
                storages.forEach { c ->
                    Card(Modifier.clickable { onOpenStorage(c) }) {
                        Icon(Icons.Filled.CloudQueue, null, tint = colors.primary, modifier = Modifier.size(44.dp))
                        Spacer(Modifier.width(14.dp))
                        Text(c.name.ifBlank { c.host }, fontSize = 15.sp, color = colors.onSurface)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun Section(label: Int) {
    Text(
        androidx.compose.ui.res.stringResource(label),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 6.dp),
    )
}

@Composable
private fun Card(modifier: Modifier, content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).then(modifier),
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, content = content)
    }
}
