package com.rajendra.msmspik

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rajendra.msmspik.platform.getDrawable
import com.rajendra.msmspik.platform.getPlatform
import com.rajendra.msmspik.platform.getThemColor
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun PListDisplay() {
    // Retrieve the app configuration data
    val appConfig = getPlatform()
    val data = appConfig.appData
    val localizationString = appConfig.appLocalization

    // Use a Material 3 Card for a visually appealing, raised container
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        // Use an elevated color scheme for visual depth
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Application Configuration",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Divider(Modifier.padding(vertical = 8.dp))

            // Display individual data fields using a helper composable
            DataItem(label = "Application ID", value = data.appId)
            DataItem(label = "Version Name", value = data.versionName)
            DataItem(label = "Build Number", value = data.version.toString())

            Text(
                text = "Application Localization",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Divider(Modifier.padding(vertical = 8.dp))

            DataItem(label = "Display name ", value = localizationString.displayName)

            Text(
                text = "Platform Drawable",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Divider(Modifier.padding(vertical = 8.dp))
            Image(getDrawable().appLogo, "App Logo")

            Text(
                text = "Platform Color ",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Divider(Modifier.padding(vertical = 8.dp))

            DataItem(label = "Primary Color ", value = localizationString.displayName, color = getThemColor().primary?:MaterialTheme.colorScheme.onSurface)
            DataItem(label = "Secondary Color ", value = localizationString.displayName, color = getThemColor().secondary?:MaterialTheme.colorScheme.onSurface)
            DataItem(label = "Accent Color ", value = localizationString.displayName, color = getThemColor().accent?:MaterialTheme.colorScheme.onSurface)

        }
    }
}

/**
 * Helper Composable to format a single configuration item (Label and Value).
 */
@Composable
fun DataItem(label: String, value: String, isPrimary: Boolean = false,color:Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isPrimary) FontWeight.SemiBold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isPrimary) MaterialTheme.colorScheme.error else color,
            fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.Medium
        )
    }
}

// --- Preview Function (for visualization in Android Studio) ---
@Preview(showBackground = true)
@Composable
fun PListDisplayPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF00668B), // Custom Blue
            error = Color(0xFFB3261E), // Standard Error Red
            surfaceContainerHigh = Color(0xFFE3F2FD), // Light Blue surface
            onSurface = Color.Black
        )
    ) {
        PListDisplay()
    }
}