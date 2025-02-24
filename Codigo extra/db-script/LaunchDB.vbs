On Error Resume Next

' Definir rutas absolutas
strPowerShell = "C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe"
strScriptPath = "C:\Users\aqua\Documents\Gastos Casa\db-script\BackupDB.ps1"

' Crear objeto Shell
Set objShell = CreateObject("WScript.Shell")

' Construir comando completo (agregando pause al final)
strCommand = """" & strPowerShell & """ -NoExit -ExecutionPolicy Bypass -Command ""& '" & strScriptPath & "'; Read-Host 'Presiona Enter para continuar...'"""

' Ejecutar el comando (1 = visible, True = esperar)
objShell.Run strCommand, 1, True

' Verificar errores
If Err.Number <> 0 Then
    MsgBox "Error al ejecutar el script: " & Err.Description, vbCritical, "Error"
End If

Set objShell = Nothing