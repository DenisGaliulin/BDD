package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    private DashboardPage dashboardPage;
    private int firstCardBalanceBefore;
    private int secondCardBalanceBefore;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

        firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        secondCardBalanceBefore = dashboardPage.getSecondCardBalance();
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        int transferAmount = secondCardBalanceBefore / 2;

        var transferPage = dashboardPage.selectFirstCardForTransfer();
        transferPage.setAmount(transferAmount);
        transferPage.setFromCard(DataHelper.getSecondCardInfo().getNumber());
        transferPage.transfer();

        int firstCardBalanceAfter = dashboardPage.getFirstCardBalance();
        int secondCardBalanceAfter = dashboardPage.getSecondCardBalance();

        assertEquals(firstCardBalanceBefore + transferAmount, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore - transferAmount, secondCardBalanceAfter);
    }

    @Test
    void shouldNotTransferMoreThanBalance() {
        int invalidAmount = secondCardBalanceBefore + 1;

        var transferPage = dashboardPage.selectFirstCardForTransfer();
        transferPage.setAmount(invalidAmount);
        transferPage.setFromCard(DataHelper.getSecondCardInfo().getNumber());
        transferPage.transfer();

        int firstCardBalanceAfter = dashboardPage.getFirstCardBalance();
        int secondCardBalanceAfter = dashboardPage.getSecondCardBalance();

        assertEquals(firstCardBalanceBefore, firstCardBalanceAfter);
        assertEquals(secondCardBalanceBefore, secondCardBalanceAfter);
    }
}