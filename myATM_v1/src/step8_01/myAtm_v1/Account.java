package step8_01.myAtm_v1;

public class Account {
	
	String number = "";
	int money = 0;
	
	public Account(String number, int money) {
		this.number = number;
		this.money = money;
	}

	void printOwnAccount() {
		System.out.printf("%s : %d\n", this.number, this.money);
	}

}
