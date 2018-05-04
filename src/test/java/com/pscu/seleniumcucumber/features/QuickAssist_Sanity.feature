@QuickAssist_Sanity
Feature: Quick Assist Smoke Test Script
Contains 4 TCs on Quick Assist to ensure environment & build stability 

@PingPageLogin_QuickAssist
Scenario: Login to Quick Assist Application through Ping Page
Given My WebApp 'QuickAssist_App' is open
When I select option 'QuicAssist Token EXternal' in dropdown 'SelectAuthenticationSystem' by 'text'
And I click 'ContinueBtn'
And I enter 'SQAtest2' in field 'Ping_Username'
And I enter 'PSCU@1234' in field 'Ping_Password'
And I enter '2113' in field 'Ping_ClientId'
And I click 'Ping_LogIN'
#And I wait for visibility of element 'SearchPopUp_Close'
#And I click 'SearchPopUp_Close'
Then I should see text 'sqatest2.21' present on page at 'QAssist_UserNameDisp'
Then I wait for '10' seconds

@SearchCard 
Scenario Outline: TC1 - Search for a Customer's Account & verify Card holder infomartion is displayed correctly in card holder info section
Given I click 'LeftPanel_SearchBtn'
And I enter '<AccountNumber>' in field 'SearchPopUp_AccountNumberField'
And I click 'SearchPopUp_Search_Btn'
And I should see text 'Card Holder Search' present on page at 'SearchSuccessToast'
And I should see text 'Found record' present on page at 'SearchSuccessToast2'
And I wait for '5' seconds
And I should see text '<FandLName>' present on page at 'CardHolderInfo_Primary'
#And I should see text '<AddrLine1>' present on page at 'CardHolderInfo_Primary_AddrLine1'
And I should see text '<AddrLine2>' present on page at 'CardHolderInfo_Primary_AddrLine2'
And I should see text '<HomePhone>' present on page at 'CardHolderInfo_HomePhone'
And I should see text '<CellPhone>' present on page at 'CardHolderInfo_Cell'
And I should see text '<BusinessPhone>' present on page at 'CardHolderInfo_Business'
And I should see text '<SSN>' present on page at 'CardHolderInfo_SSN'
And I should see text '<DOB>' present on page at 'CardHolderInfo_DOB'

Examples:
|AccountNumber   |FandLName          |AddrLine1          |AddrLine2               |HomePhone     |CellPhone     |BusinessPhone |SSN		   |DOB		  |
|4429005030009751|PRIMARY TESTACCOUNT|6855 PACIFIC STREET|BOILING SPRINGS,SC 29316|(111) 111-1111|(222) 222-2222|(333) 333-3333|XXX-XX-6789|01/01/1970|
|4108836000011270|JANE DOE			 |737 PLANTATION RD	 |LANCASTER,SC 29720-5808 | 			 |				|			   |XXX-XX-1324|01/01/1970|


@AddSecondaryUser
Scenario: TC2 - Add Secondary user to Account and verify successful addition
When I scroll to 'coordinates' - '0,400' 
And I wait for '2' seconds
Given I click 'AccountUsers_AddUser'
Then I should see text 'Add Account User' present on page at 'AddUserPopUp_Header'  
When I select 'AddUserPopUp_SelectType_Secondary' by clicking 'AddUserPopUp_SelectType'
And I enter 'TestUserFName' in field 'AddUserPopUp_FirstName'
And I enter 'TestUserLName' in field 'AddUserPopUp_LastName'
And I enter '123456789' in field 'AddUserPopUp_SSN'
And I enter '05/25/1985' in field 'AddUserPopUp_DOB'
And I select 'AddUserPopUp_CBR_DoNotReport' by clicking 'AddUserPopUp_CBR'
And I enter '1111231234' in field 'AddUserPopUp_HomePhone'
And I enter '3213211234' in field 'AddUserPopUp_CellPhone'
And I enter '1233211234' in field 'AddUserPopUp_WorkPhone'
And I enter 'test@testSecondary.user' in field 'AddUserPopUp_Email'
#And I scroll to 'coordinates' - '0,500'
And I click 'AddUserPopUp_Confirm'
And I should see text 'Secondary User Added Successfully' present on page at 'AddUser_SuccessMessage'

@SearchCard 
Scenario Outline: TC1 - Search for a Customer's Account & verify Card holder infomartion is displayed correctly in card holder info section
Given I click 'LeftPanel_SearchBtn'
And I enter '<AccountNumber>' in field 'SearchPopUp_AccountNumberField'
And I click 'SearchPopUp_Search_Btn'
And I should see text 'Card Holder Search' present on page at 'SearchSuccessToast'
And I should see text 'Found record' present on page at 'SearchSuccessToast2'
And I wait for '5' seconds

Examples:
|AccountNumber   |FandLName          |AddrLine1          |AddrLine2               |HomePhone     |CellPhone     |BusinessPhone |SSN		   |DOB		  |
|4429005030009751|PRIMARY TESTACCOUNT|6855 PACIFIC STREET|BOILING SPRINGS,SC 29316|(111) 111-1111|(222) 222-2222|(333) 333-3333|XXX-XX-6789|01/01/1970|

@DeleteSecondaryUser 
Scenario: TC4 - Verify deleting the additional card holder is successful (TC4 - Delete Secondary user)
Given I scroll to 'coordinates' - '0,500'
And I wait for '3' seconds
#Given I Click 'AccountUsers_DeleteUser' with reference to text: 'SECONDARY'
Given I click 'AccountUsers_DeleteUser'
Then I should see element 'AccountUsers_DeleteUserPopUpHeader' present on page_
And I click 'AccountUsers_DeleteUserPopUpConfirm'
Then I should see text 'Account User' present on page at 'AccountUsers_DeleteConfirmation1'
Then I should see text 'Delete Success' present on page at 'AccountUsers_DeleteConfirmation2'

@PaymentRequest 
Scenario: TC3 - Verify one time payment request is successful
Given I click 'QAssist_MainMenu_Payments'
And I scroll to 'coordinates' - '0,500'
#And I should see text 'One Time Payment' present on page at 'Payments_OneTimePaymentW'
And I click 'Payments_OneTimePaymentW_AmountDD'
And I wait for '2' seconds
And I click 'Payments_OneTimePaymentW_MinimumPayment'
And I wait for '10' seconds