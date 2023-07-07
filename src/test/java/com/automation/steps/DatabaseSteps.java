package com.automation.steps;

import com.automation.utils.ConfigReader;
import com.automation.utils.DatabaseUtils;
import io.cucumber.java.en.And;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSteps {
    @And("verify new account details updated in database")
    public void verifyNewAccountDetailsUpdatedInDatabase() throws SQLException, InterruptedException {
        String accountNum = ConfigReader.getProperty("account.number");
        String query = "select * from account where account_number = '%s';";
        ResultSet rs = DatabaseUtils.executeQuery(String.format(query, accountNum));
        rs.next();

        double openingBalanceDB = rs.getDouble("opening_balance");
        double openingBalanceUI = Double.valueOf(ConfigReader.getProperty("transaction.amount"));
        System.out.println("Balance from DB "+openingBalanceDB);
        System.out.println("Balance from UI "+openingBalanceUI);
        Assert.assertTrue(openingBalanceDB==openingBalanceUI);

        String accountNameUI=ConfigReader.getProperty("account.name");
        String accountNameDB=rs.getString("name");
        System.out.println("Account name from DB "+accountNameDB);
        System.out.println("Account name from UI "+accountNameUI);
        Assert.assertTrue(accountNameDB.equals(accountNameUI));
    }

        @And("validate new user created in database")
        public void validateNewUserCreatedInDatabase() throws SQLException {
            String emailAPI = ConfigReader.getProperty("api.email");
            String query = "select * from users inner join user_profile\n" +
                    "ON users.profile_id = user_profile.id\n" +
                    "where username = '%s';";
            ResultSet rs = DatabaseUtils.executeQuery(String.format(query, emailAPI));
            rs.next();

            String emailDB = rs.getString("email_address");
            String ssnDB = rs.getString("ssn").replace("-","");
            String ssnAPI = ConfigReader.getProperty("api.ssn");

            System.out.println("Email from DB " + emailDB);
            System.out.println("SSN from DB " + ssnDB);
            System.out.println("Email from API " + emailAPI);
            System.out.println("SSN from API " + ssnAPI);

            Assert.assertEquals(emailAPI, emailDB);
            Assert.assertEquals(ssnDB, ssnAPI);
        }
    }
