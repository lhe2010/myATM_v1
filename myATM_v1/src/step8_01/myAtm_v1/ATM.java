package step8_01.myAtm_v1;

import java.util.Scanner;

public class ATM {
	
	Scanner scan = new Scanner(System.in);
	
	UserManager manager = new UserManager();
	int identifier = -1;
	
	void printMainMenu() {
		while(true) {
			System.out.println("\n[ATM프로그램동작중...]");
			System.out.println("[1] 로그인 [2] 로그아웃 [3] 회원가입 [4] 회원탈퇴 [5] 회원출력 [0] 종료");
			System.out.print("메뉴를 선택하세요 : ");
			
			int sel = scan.nextInt();
			
			if(sel == 1) 		login();	 
			else if(sel == 2)	logout();	
			else if(sel == 3) 	join();
			else if(sel == 4) 	leave();
			else if(sel == 5)	manager.printAllUser();
			else if(sel == 0) {
				System.out.println("[ATM프로그램 종료]");
				break;
			}
		}
	}
	
	void login() {
		if(identifier != -1) {
			System.out.println("이미 로그인 되어있습니다. ");
			printAccountMenu();
		} else {
			this.identifier = manager.logUser();
			if(identifier == -1)	
				System.out.println("로그인실패");
			else 					
				printAccountMenu();
		}	
	}
	
	void logout() {
		if(identifier == -1) {
			System.out.println("로그인 이후 이용할 수 있는 메뉴입니다. ");
		} else {
			System.out.printf("로그아웃 완료. %s님 안녕히가세요:)\n", manager.users[identifier].name);
			identifier = -1;
		}
	}
	
	void join() {
		if(identifier != -1) {
			System.out.println("로그아웃 이후 이용할 수 있는 메뉴입니다. ");
		} else {
			manager.addUser();			
		}
	}
	
	void leave() {
		manager.leave();
	}
	
	void printAccountMenu() {
		User currentUsr = manager.getUser(identifier);

		while (true) {
			System.out.print("[1.계좌생성] [2.계좌삭제] [3.조회] [0.뒤로가기] : ");
			int sel = scan.nextInt();
			if 	(sel == 1) {
				System.out.printf("%s님, 계좌를 만들겠습니다. \n", currentUsr.name);
				Account tempAcc = manager.addAccount(identifier);
				System.out.print("새로운 계좌에 돈을 넣으시려면 금액을 입력하세요(0:다음에 넣기): ");
				int money = scan.nextInt();
				if(money != 0) {
					tempAcc.money += money;
				}
				System.out.printf("%s님의 새로운 계좌번호는 %s입니다.\n", currentUsr.name, tempAcc.number);
			} 		
			else if (sel == 2) {
				System.out.printf("%s님이 가지고 있는 계좌는 다음과 같습니다. \n",currentUsr.name);
				manager.users[identifier].printAccount();
				System.out.print("삭제하고싶은 계좌번호를 입력하세요(0:뒤로가기): ");
				String number = scan.next();
				if(number.equals("0")) break;
				int delResult = manager.delAccount(identifier, number);
				if( delResult == -1) {
					System.out.println("없는 계좌번호입니다. ");
					continue;
				} else {
					System.out.printf("계좌번호 %s를 삭제했습니다. 잔액 %d를 가져가세요.\n", number, delResult);
				}
				
			} 	
			else if (sel == 3) {
				System.out.printf("%s님이 가지고 있는 계좌는 다음과 같습니다. \n",currentUsr.name);
				manager.users[identifier].printAccount();
			}  	  
			else if (sel == 0) 
				break;
		}
	}	
}
