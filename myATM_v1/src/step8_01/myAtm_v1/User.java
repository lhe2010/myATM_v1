package step8_01.myAtm_v1;

public class User {
	String name = "";
	int accCount = 0;
	Account[] accs = null;
	
	public User(String name) {
		this.name = name;
		this.accCount = 0;
		this.accs = null;
	}

	void printAccount() {
		System.out.printf("UserName : %s\n", name);
		if(accCount == 0) {
			System.out.printf("아직 %s고객님은 계좌를 개설하지 않으셨네요.\n", name);
		} else {
			for (Account acc : accs) 
				acc.printOwnAccount();
		}
	}

}
