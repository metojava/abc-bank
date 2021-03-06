package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
		case CHECKING:
			return amount * 0.001;
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return (amount - 1000) * 0.002;
			// case SUPER_SAVINGS:
			// if (amount <= 4000)
			// return 20;
		case MAXI_SAVINGS:
			if (tenDays())
				return amount * 0.05;
			return amount * 0.001;
		default:
			return amount * 0.001;
		}
	}

	public double dailyAddedInterest() {	
		if (DateProvider.getInstance().getLeap())
			return interestEarned() / 366;
		return interestEarned() / 365;
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public boolean tenDays() {
		DateProvider dp = DateProvider.getInstance();
		Date now = dp.now();
		boolean isok = true;
		for (Transaction t : transactions) {
			if (t.amount < 0) {
				if (dp.dateDiff(now, t.getTransactionDate()) > 10)
					isok = false;
			}
		}
		return isok;
	}

	public int getAccountType() {
		return accountType;
	}

}
