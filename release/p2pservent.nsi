;--------------------------------
;Include Modern UI
!include "MUI.nsh"

;--------------------------------
;General
  !define MAIN_DIR "F:\SAICON\P2PServent"
  !define VERSION "v1.08"
  !define RELEASE_DIR "${MAIN_DIR}\release"
  !define SRC_DIR "${MAIN_DIR}"

;Name and file
  Name "iShare ${VERSION}"
  OutFile "iShare_${VERSION}.exe"
  
;Default installation folder
  InstallDir "$PROGRAMFILES\GamersUniverse\iShare"
  
;Get installation folder from registry if available
  InstallDirRegKey HKEY_LOCAL_MACHINE "SOFTWARE\GamersUniverse\iShare_${VERSION}" ""
  
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
  !define MUI_FINISHPAGE_RUN "$INSTDIR\iShare.exe"
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
Section "iShare ${VERSION}" ; (default section)

SetOutPath "$INSTDIR\images"
File "${SRC_DIR}\images\About.png"
File "${SRC_DIR}\images\Add_user.png"
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
File "${SRC_DIR}\images\GamersLogo.png"
File "${SRC_DIR}\images\igamerbtn.gif"
File "${SRC_DIR}\images\exitbtn.gif"
File "${SRC_DIR}\images\mydownloads.png"
File "${SRC_DIR}\images\myfriends.png"
File "${SRC_DIR}\images\mypeers.png"
File "${SRC_DIR}\images\mysearch.png"
File "${SRC_DIR}\images\mysettings.png"
File "${SRC_DIR}\images\myshared.png"
File "${SRC_DIR}\images\myuploads.png"
File "${SRC_DIR}\images\helpabout.png"
File "${SRC_DIR}\images\helpupdates.png"

SetOutPath "$INSTDIR"
File "${RELEASE_DIR}\iShare.exe"
File "${RELEASE_DIR}\iShareUpdater.exe"

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
File "${SRC_DIR}\lib\jdic.jar"
File "${SRC_DIR}\lib\jdic.dll"
File "${SRC_DIR}\lib\jdic_stub.jar"

SetOutPath "$INSTDIR"

CreateDirectory "$SMPROGRAMS\GamersUniverse"
CreateShortCut "$SMPROGRAMS\GamersUniverse\iShare ${VERSION}.lnk" "$INSTDIR\iShare.exe"
CreateShortCut "$SMPROGRAMS\GamersUniverse\Uninstall iShare ${VERSION}.lnk" "$INSTDIR\uninst.exe"

;Store installation folder
WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\iShare" "" "$INSTDIR"
;Store uninstall infos folder
WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\iShare" "DisplayName" "iShare ${VERSION} (remove only)"
WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\iShare" "UninstallString" '"$INSTDIR\uninst.exe"'
; write out uninstaller
WriteUninstaller "$INSTDIR\uninst.exe"

SectionEnd ; end of default section

Section "Desktop Shortcut"

SetOutPath "$INSTDIR"
CreateShortCut "$DESKTOP\iShare.lnk" "$INSTDIR\iShare.exe"

SectionEnd ; end of default section

; begin uninstall settings/section
UninstallText "This will uninstall iShare from your system"

Section un.iShare ${VERSION}
; add delete commands to delete whatever files/registry keys/etc you installed here.
Delete "$INSTDIR\iShare.exe"

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
Delete "$INSTDIR\lib\jdic.jar"
Delete "$INSTDIR\lib\jdic.dll"
Delete "$INSTDIR\lib\jdic_stub.jar"

Delete "$INSTDIR\images\About.png"
Delete "$INSTDIR\images\Add_user.png"
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
Delete "$INSTDIR\images\GamersLogo.png"
Delete "$INSTDIR\images\igamerbtn.gif"
Delete "$INSTDIR\images\exitbtn.gif"
Delete "$INSTDIR\images\mydownloads.png"
Delete "$INSTDIR\images\myfriends.png"
Delete "$INSTDIR\images\mypeers.png"
Delete "$INSTDIR\images\mysearch.png"
Delete "$INSTDIR\images\mysettings.png"
Delete "$INSTDIR\images\myshared.png"
Delete "$INSTDIR\images\myuploads.png"
Delete "$INSTDIR\images\helpabout.png"
Delete "$INSTDIR\images\helpupdates.png"

Delete "$DESKTOP\iShare.lnk"
Delete "$SMPROGRAMS\GamersUniverse\iShare ${VERSION}.lnk"
Delete "$SMPROGRAMS\GamersUniverse\Uninstall iShare ${VERSION}.lnk"
RMDir "$SMPROGRAMS\GamersUniverse"

DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\iShare"
DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\iShare"

RMDir "$INSTDIR\images"
RMDir "$INSTDIR\lib"
Delete "$INSTDIR\uninst.exe"
RMDir "$INSTDIR"
SectionEnd ; end of uninstall section

Section /o "un.iShare User Configuration"
; add delete commands to delete whatever files/registry keys/etc you installed here.
RMDir /r "$INSTDIR"
SectionEnd ; end of uninstall section

; eof