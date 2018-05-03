package com.pscu.seleniumcucumber.pageObjects.WebApp;

import com.pscu.seleniumcucumber.framework.HashMapContainer;
import com.pscu.seleniumcucumber.pageObjects.GetPageObject;

public class QuickAssist_App{

	static GetPageObject PO = new GetPageObject();

	//Aramark Login Page 
	public static void PingPage_SignIn(){
		PO.add("url::https://sqapa.pscu.com/quickassist");
		PO.add("SelectAuthenticationSystem::name=pfidpadapterid");
		PO.add("ContinueBtn::xpath=//input[@type='submit']");
		
		PO.add("Ping_Username::id=username");
		PO.add("Ping_Password::id=password");
		PO.add("Ping_ClientId::id=clientId");
		PO.add("Ping_LogIN::xpath=//a[@title='Log In']");		
	}
	
	public static void QuickAssist_HomePage(){
		/*Quick Assist Header Main Menu Elements                                                   */
		PO.add("QAssist_MainMenu_Payments::xpath=//li[3]/a");
		
		/*Homepage Elements                                                   */
		PO.add("SearchPopUp_Close::xpath=//button[@class='close']");
		PO.add("QAssist_UserNameDisp::xpath=//div[@class='usernameWrapper']");
		PO.add("LeftPanel_SearchBtn::xpath=(//a[contains(text(),'Search')])[2]");
		PO.add("SearchPopUp_AccountNumberField::id=searchAcctNumber");
		PO.add("SearchPopUp_Search_Btn::xpath=//div[@class='form-actions right']/button[@type='submit']");
		PO.add("SearchSuccessToast::xpath=//div[@ng-class='config.title']");
		PO.add("SearchSuccessToast2::xpath=//div[@ng-switch-when='trustedHtml']");
		
		//Card Holder Info (Side Panel)
		PO.add("CardHolderInfo_Primary::xpath=//span[@class='fontSize120 ng-binding']");
		//PO.add("CardHolderInfo_Primary_AddrLine1::xpath=//span[@class='fontSize120 ng-binding']/..//div[2]/..//br");
		PO.add("CardHolderInfo_Primary_AddrLine2::xpath=//span[@class='fontSize120 ng-binding']/..//div[2]");
		PO.add("CardHolderInfo_HomePhone::xpath=//tr/td[2]");
		PO.add("CardHolderInfo_Cell::xpath=//tr[2]/td[2]");
		PO.add("CardHolderInfo_Business::xpath=//tr[3]/td[2]");
		PO.add("CardHolderInfo_SSN::xpath=//tr[4]/td[2]");
		PO.add("CardHolderInfo_DOB::xpath=//tr[5]/td[2]");
		
		//Account users section
		PO.add("AccountUsers_AddUser::xpath=//div[@data-ng-controller='AccountUsersController']/div/div/div[3]/a");
		//PO.add("AccountUsers_DeleteUser::xpath=//td[contains(text(),' replaceFirstName ')]/..//td[7]/a[2]");
		PO.add("AccountUsers_DeleteUser::xpath=//td[contains(text(),'SECONDARY')]/..//td[7]/a[2]");
		PO.add("AccountUsers_DeleteUserPopUpHeader::xpath=//h3[@class='modal-title ng-binding'][contains(text(),'Delete account user')]");
		PO.add("AccountUsers_DeleteUserPopUpCancel::xpath=//button[contains(text(),'Cancel')]");
		PO.add("AccountUsers_DeleteUserPopUpConfirm::xpath=//button[contains(text(),'Confirm')]");
		PO.add("AccountUsers_DeleteConfirmation1::xpath=//div[@ng-class='config.title']");
		PO.add("AccountUsers_DeleteConfirmation2::xpath=//div[@ng-switch-when='trustedHtml']");
		
		
		//Add User Pop-up
		PO.add("AddUserPopUp_Header::xpath=//div[@class='modal-dialog']/..//h3[@class='modal-title ng-binding'][contains(text(),'Add Account User')]");
		PO.add("AddUserPopUp_SelectType::xpath=//form[@name='AccountUserForm']/..//span[@class='select2-arrow ui-select-toggle']");
		PO.add("AddUserPopUp_SelectType_Secondary::xpath=//span[contains(text(),'Secondary')]");
		PO.add("AddUserPopUp_FirstName::xpath=//input[@name='uxFirstName']");
		PO.add("AddUserPopUp_LastName::xpath=//input[@name='uxLastName']");
		PO.add("AddUserPopUp_SSN::xpath=//input[@name='uxSSN']");
		PO.add("AddUserPopUp_DOB::xpath=//input[@name='uxDOB']");
		PO.add("AddUserPopUp_CBR::xpath=//a[@class='select2-choice ui-select-match select2-default']");
		PO.add("AddUserPopUp_CBR_DoNotReport::xpath=//span[contains(text(),'Do not report')]");
		PO.add("AddUserPopUp_HomePhone::xpath=//input[@ng-model='accountUserForm.phoneHome']");
		PO.add("AddUserPopUp_CellPhone::xpath=//input[@ng-model='accountUserForm.phoneCell']");
		PO.add("AddUserPopUp_WorkPhone::xpath=//input[@ng-model='accountUserForm.phoneWork']");
		PO.add("AddUserPopUp_Email::xpath=//input[@ng-model='accountUserForm.email']");
		PO.add("AddUserPopUp_Cancel::xpath=//button[@ng-click='cancel()']");
		PO.add("AddUserPopUp_Confirm::xpath=//button[@ng-click='accountUserForm.submit(AccountUserForm)']");
		PO.add("AddUser_SuccessMessage::xpath=//div[@ng-switch-when='trustedHtml']");
	}

	public static void QuickAssist_PaymentsPage(){
		
		PO.add("Payments_OneTimePaymentW::xpath=//div[@data-ng-controller='OneTimePaymentController']");
		PO.add("Payments_OneTimePaymentW_AmountDD::xpath=//span[@class='select2-arrow ui-select-toggle']");
		PO.add("Payments_OneTimePaymentW_MinimumPayment::xpath//span[contains(text(),'Minimum Payment')]");
	}

	//Load PageObjects 
	public static void loadallPageObjects(){
		PingPage_SignIn();
		QuickAssist_HomePage();
		QuickAssist_PaymentsPage();
		System.out.println("Object Load to HM Successfull!");
	}

}
