Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

# Crear la ventana principal
$form = New-Object System.Windows.Forms.Form
$form.Text = "DB Manager - Gastos Casa"
$form.Size = New-Object System.Drawing.Size(800,500)
$form.StartPosition = "CenterScreen"
$form.BackColor = [System.Drawing.Color]::White

# Función para actualizar la lista de backups
function Update-BackupList {
    $backupPath = "C:\Users\aqua\Documents\Gastos Casa\DB-gastos-casa-BackUp"
    $listBox.Items.Clear()
    
    # Obtener todos los archivos y mostrar el conteo
    $allFiles = Get-ChildItem $backupPath -Filter "backup-gastos-casa_*.sql"
    $totalFiles = $allFiles.Count
    [System.Windows.Forms.MessageBox]::Show("Total de archivos encontrados: $totalFiles", "Info")
    
    # Mostrar los 6 más recientes
    $allFiles | 
        Sort-Object LastWriteTime -Descending | 
        Select-Object -First 6 | 
        ForEach-Object {
            $fecha = $_.LastWriteTime.ToString("dd/MM/yyyy HH:mm:ss")
            $listBox.Items.Add("$fecha - $($_.Name)")
        }
}

# Crear botones
$btnLocalBackup = New-Object System.Windows.Forms.Button
$btnLocalBackup.Location = New-Object System.Drawing.Point(20,20)
$btnLocalBackup.Size = New-Object System.Drawing.Size(760,40)
$btnLocalBackup.Text = "Crear Nuevo Backup Local"
$btnLocalBackup.BackColor = [System.Drawing.Color]::LightGreen
$btnLocalBackup.Add_Click({
    try {
        # Establecer contraseña
        $env:PGPASSWORD = "123"
        
        # Crear el backup
        $backupPath = "C:\Users\aqua\Documents\Gastos Casa\DB-gastos-casa-BackUp"
        $backupFile = "backup-gastos-casa_$(Get-Date -Format 'yyyyMMdd_HHmmss').sql"
        
        & "C:\Program Files\PostgreSQL\14\bin\pg_dump" "-h" "localhost" "-U" "dkinkel0" "-d" "gastos-casa_db" "-f" "$backupPath\$backupFile"
        
        if ($LASTEXITCODE -eq 0) {
            [System.Windows.Forms.MessageBox]::Show("Backup creado exitosamente`nArchivo: $backupFile", "Éxito")
            
            # Actualizar la lista de backups
            Update-BackupList
        }
    }
    catch {
        [System.Windows.Forms.MessageBox]::Show("Error al crear backup: $_", "Error")
    }
    finally {
        # Limpiar la variable de contraseña
        $env:PGPASSWORD = ""
    }
})

$btnUploadDrive = New-Object System.Windows.Forms.Button
$btnUploadDrive.Location = New-Object System.Drawing.Point(20,70)
$btnUploadDrive.Size = New-Object System.Drawing.Size(760,40)
$btnUploadDrive.Text = "Subir Backup a Google Drive"
$btnUploadDrive.BackColor = [System.Drawing.Color]::LightBlue
$btnUploadDrive.Add_Click({
    if ($listBox.SelectedItem) {
        # Extraer el nombre del archivo del item seleccionado
        $selectedBackup = $listBox.SelectedItem.ToString()
        $fileName = $selectedBackup.Split(" - ")[1].Trim()  # Obtiene solo el nombre del archivo y elimina espacios
        
        # Usar Join-Path para manejar la ruta correctamente
        $backupPath = Join-Path "C:\Users\aqua\Documents\Gastos Casa\DB-gastos-casa-BackUp" $fileName
        
        try {
            # Ejecutar rclone copyto usando la ruta entre comillas
            $result = & rclone copyto "`"$backupPath`"" "gdrive:DBDrive-gastos-casa/$fileName"
            
            if ($LASTEXITCODE -eq 0) {
                [System.Windows.Forms.MessageBox]::Show("Backup subido exitosamente a Google Drive`nArchivo: $fileName", "Éxito")
            } else {
                throw "Error al subir el archivo"
            }
        }
        catch {
            [System.Windows.Forms.MessageBox]::Show("Error al subir a Google Drive: $_", "Error")
        }
    } else {
        [System.Windows.Forms.MessageBox]::Show("Por favor, selecciona un backup para subir", "Aviso")
    }
})

$btnDownloadDrive = New-Object System.Windows.Forms.Button
$btnDownloadDrive.Location = New-Object System.Drawing.Point(20,120)
$btnDownloadDrive.Size = New-Object System.Drawing.Size(760,40)
$btnDownloadDrive.Text = "Bajar Backup de Google Drive"
$btnDownloadDrive.BackColor = [System.Drawing.Color]::LightYellow
$btnDownloadDrive.Add_Click({
    [System.Windows.Forms.MessageBox]::Show("Bajando de Google Drive...")
})

$btnUpdateDB = New-Object System.Windows.Forms.Button
$btnUpdateDB.Location = New-Object System.Drawing.Point(20,170)
$btnUpdateDB.Size = New-Object System.Drawing.Size(760,40)
$btnUpdateDB.Text = "Actualizar Base de Datos"
$btnUpdateDB.BackColor = [System.Drawing.Color]::LightCoral
$btnUpdateDB.Add_Click({
    [System.Windows.Forms.MessageBox]::Show("Actualizando base de datos...")
})

# Crear ListBox para mostrar backups
$listBox = New-Object System.Windows.Forms.ListBox
$listBox.Location = New-Object System.Drawing.Point(20,230)
$listBox.Size = New-Object System.Drawing.Size(760,220)
$listBox.Font = New-Object System.Drawing.Font("Consolas", 9)
$listBox.HorizontalScrollbar = $true

# Agregar controles al formulario
$form.Controls.Add($btnLocalBackup)
$form.Controls.Add($btnUploadDrive)
$form.Controls.Add($btnDownloadDrive)
$form.Controls.Add($btnUpdateDB)
$form.Controls.Add($listBox)

# Cargar la lista inicial de backups
Update-BackupList

# Mostrar el formulario
$form.ShowDialog()