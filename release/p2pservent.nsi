;--------------------------------
;Include Modern UI
!include "MUI.nsh"

;--------------------------------
;General
  !define VERSION "v0.93"
  !define RELEASE_DIR "C:\DISK_F\SAICON\P2PServent\release"
  !define SRC_DIR "C:\DISK_F\SAICON\P2PServent"

;Name and file
  Name "P2PServent ${VERSION}"
  OutFile "Setup.exe"
  
;Default installation folder
  InstallDir "$PROGRAMFILES\GamersUniverse\P2PServent_${VERSION}"
  
;Get installation folder from registry if available
  InstallDirRegKey HKEY_LOCAL_MACHINE "SOFTWARE\GamersUniverse\P2PServent_${VERSION}" ""
  
  !cd ${RELEASE_DIR}

;--------------------------------
;Interface Settings

  !define MUI_ABORTWARNING
  XPStyle on

;--------------------------------
;Pages
  !insertmacro MUI_PAGE_WELCOME
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_INSTFILES
  !define MUI_FINISHPAGE_RUN "$INSTDIR\P2PServent.exe"
  !define MUI_FINISHPAGE_LINK "Visit GamersUniverse on the web"
  !define MUI_FINISHPAGE_LINK_LOCATION http://www.gamersuniverse.com
  !define MUI_FINISHPAGE_NOREBOOTSUPPORT
  !insertmacro MUI_PAGE_FINISH
  
  !insertmacro MUI_UNPAGE_WELCOME
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_COMPONENTS
  !insertmacro MUI_UNPAGE_INSTFILES
  !insertmacro MUI_UNPAGE_FINISH

;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"
  

CRCCheck on

;--------------------------------
;Installer Sections
Section "P2PServent ${VERSION}" ; (default section)

SetOutPath "$INSTDIR\images"
File "${SRC_DIR}\images\About.png"
File "${SRC_DIR}\images\Add_user.png"
File "${SRC_DIR}\images\AddFolder.png"
File "${SRC_DIR}\images\Administrator.png"
File "${SRC_DIR}\images\Connect.png"
File "${SRC_DIR}\images\DelFolder.png"
File "${SRC_DIR}\images\Disconnect.png"
File "${SRC_DIR}\images\Download.png"
File "${SRC_DIR}\images\Exit.png"
File "${SRC_DIR}\images\Folder.png"
File "${SRC_DIR}\images\gamers.png"
File "${SRC_DIR}\images\Peers.png"
File "${SRC_DIR}\images\Remove_user.png"
File "${SRC_DIR}\images\SearchFile.png"
File "${SRC_DIR}\images\Settings.png"
File "${SRC_DIR}\images\Upload.png"
File "${SRC_DIR}\images\View_users.png"
File "${SRC_DIR}\images\ViewlFolder.png"

SetOutPath "$INSTDIR"
File "${RELEASE_DIR}\P2PServent.exe"

SetOutPath "$INSTDIR\lib"
File "${SRC_DIR}\lib\activation.jar"
File "${SRC_DIR}\lib\axis.jar"
File "${SRC_DIR}\lib\commons-collections-3.2.jar"
File "${SRC_DIR}\lib\commons-discovery-0.2.jar"
File "${SRC_DIR}\lib\commons-httpclient-3.0.1.jar"
File "${SRC_DIR}\lib\commons-logging.jar"
File "${SRC_DIR}\lib\forms_rt.jar"
File "${SRC_DIR}\lib\forms-1.1.0.jar"
File "${SRC_DIR}\lib\jaxrpc.jar"
File "${SRC_DIR}\lib\log4j.jar"
File "${SRC_DIR}\lib\looks-2.1.4.jar"
File "${SRC_DIR}\lib\mailapi.jar"
File "${SRC_DIR}\lib\MRJAdapter.jar"
File "${SRC_DIR}\lib\svnphex.jar"
File "${SRC_DIR}\lib\wsdl4j-1.5.1.jar"

SetOutPath "$INSTDIR"

CreateDirectory "$SMPROGRAMS\GamersUniverse"
CreateShortCut "$SMPROGRAMS\GamersUniverse\P2PServent ${VERSION}.lnk" "$INSTDIR\P2PServent.exe"
CreateShortCut "$SMPROGRAMS\GamersUniverse\Uninstall P2PServent ${VERSION}.lnk" "$INSTDIR\uninst.exe"

;Store installation folder
WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\P2PServent_${VERSION}" "" "$INSTDIR"
;Store uninstall infos folder
WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\P2PServent_${VERSION}" "DisplayName" "P2PServent ${VERSION} (remove only)"
WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\P2PServent_${VERSION}" "UninstallString" '"$INSTDIR\uninst.exe"'
; write out uninstaller
WriteUninstaller "$INSTDIR\uninst.exe"

SectionEnd ; end of default section

Section "Desktop Shortcut"

SetOutPath "$INSTDIR"
CreateShortCut "$DESKTOP\P2PServent.lnk" "$INSTDIR\P2PServent.exe"

SectionEnd ; end of default section

; begin uninstall settings/section
UninstallText "This will uninstall P2PServent from your system"

Section un.P2PServent ${VERSION}
; add delete commands to delete whatever files/registry keys/etc you installed here.
Delete "$INSTDIR\P2PServent.exe"

Delete "$INSTDIR\lib\activation.jar"
Delete "$INSTDIR\lib\axis.jar"
Delete "$INSTDIR\lib\commons-collections-3.2.jar"
Delete "$INSTDIR\lib\commons-discovery-0.2.jar"
Delete "$INSTDIR\lib\commons-httpclient-3.0.1.jar"
Delete "$INSTDIR\lib\commons-logging.jar"
Delete "$INSTDIR\lib\forms_rt.jar"
Delete "$INSTDIR\lib\forms-1.1.0.jar"
Delete "$INSTDIR\lib\jaxrpc.jar"
Delete "$INSTDIR\lib\log4j.jar"
Delete "$INSTDIR\lib\looks-2.1.4.jar"
Delete "$INSTDIR\lib\mailapi.jar"
Delete "$INSTDIR\lib\MRJAdapter.jar"
Delete "$INSTDIR\lib\svnphex.jar"
Delete "$INSTDIR\lib\wsdl4j-1.5.1.jar"

Delete "$INSTDIR\images\About.png"
Delete "$INSTDIR\images\Add_user.png"
Delete "$INSTDIR\images\AddFolder.png"
Delete "$INSTDIR\images\Administrator.png"
Delete "$INSTDIR\images\Connect.png"
Delete "$INSTDIR\images\DelFolder.png"
Delete "$INSTDIR\images\Disconnect.png"
Delete "$INSTDIR\images\Download.png"
Delete "$INSTDIR\images\Exit.png"
Delete "$INSTDIR\images\Folder.png"
Delete "$INSTDIR\images\gamers.png"
Delete "$INSTDIR\images\Peers.png"
Delete "$INSTDIR\images\Remove_user.png"
Delete "$INSTDIR\images\SearchFile.png"
Delete "$INSTDIR\images\Settings.png"
Delete "$INSTDIR\images\Upload.png"
Delete "$INSTDIR\images\View_users.png"
Delete "$INSTDIR\images\ViewlFolder.png"

Delete "$DESKTOP\P2PServent.lnk"
Delete "$SMPROGRAMS\GamersUniverse\P2PServent ${VERSION}.lnk"
Delete "$SMPROGRAMS\GamersUniverse\Uninstall P2PServent ${VERSION}.lnk"
RMDir "$SMPROGRAMS\GamersUniverse"

DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\P2PServent_${VERSION}"
DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\P2PServent_${VERSION}"

RMDir "$INSTDIR\images"
RMDir "$INSTDIR\lib"
Delete "$INSTDIR\uninst.exe"
RMDir "$INSTDIR"
SectionEnd ; end of uninstall section

Section /o "un.P2PServent User Configuration"
; add delete commands to delete whatever files/registry keys/etc you installed here.
RMDir /r "$INSTDIR"
SectionEnd ; end of uninstall section

; eof