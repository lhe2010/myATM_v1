package step8_01.myAtm_v1;

import java.util.Random;
import java.util.Scanner;

public class UserManager {
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	
	User[] users = null;
	int userCount = 0;
	
	void addUser() {
		String tempName="";
		
		while(true) {
			System.out.print("가입하실 유저아이디 : ");
			tempName = scan.next();
			
			boolean isAlready = false;
			// 이미 존재하는 유저인지 확인
			for (int i = 0; i < userCount; i++) {
				if(users[i].name.equals(tempName)) 
					isAlready = true;
			}
			// 이미 존재한다면 빠꾸
			if(isAlready) {
				System.out.println("이미 있는 아이디 입니다. 다시 입력하세요. ");
				continue;
			} else
				break;
		}
		// 유효한 아이디 입력한 경우 진행
		User[] temp = users;
		users = new User[userCount+1];
		for (int i = 0; i < userCount; i++) {
			users[i] = temp[i];
		}
		users[userCount++] = new User(tempName);
		System.out.printf("회원가입 완료. %s님 환영합니다:)\n", tempName);
	}

	User getUser(int idx) {
		return users[idx];
	}
	
	int logUser(){ // *****
		int identifier = -1;
		while(true) {
			System.out.print("로그인하실 아이디 : ");
			String tempName = scan.next();
			
			for (int i = 0; i < userCount; i++) {
				// 올바른 아이디인 경우 로그인!
				if(users[i].name.equals(tempName)) 
					identifier = i;
			}	
			return identifier;
			// nExist보다 그냥 identifier를 이용하는게 좋을거같다(정답T)
			// nExist == -1말고 identifier를 리턴해서 -1이면 없는것이라고 판단 가능하기 때문이다. 
			// 즉 아래 코드 안써도됨. 
//			if(nExist == -1) {
//				System.out.println("존재하지 않는 아이디 입니다. ");
//			} else {
//				return nExist;
//			}
		}
	}
	
	void leave() {
		int nExist = -1;
		while(true) {
			System.out.print("탈퇴하실 아이디 : ");
			String tempName = scan.next();
			
			// 아이디가 존재하는지 체크
			for (int i = 0; i < userCount; i++) {
				if(users[i].name.equals(tempName))
					nExist = i;
			}
			
			if (nExist == -1) {
				System.out.println("존재하지 않는 아이디 입니다. ");
				continue;
			} else {
				break;
			}
		}
		// 올바른 아이디인 경우 탈퇴진행 
		User[] tempUsrs = users;
		users = new User[userCount-1];
			
		for (int i = 0; i < nExist; i++) {
			users[i] = tempUsrs[i];
//			this.getUser(i) = tempUsrs[i]; // 안됨!!!! 
		}
		for (int i = nExist; i < users.length; i++) {
			users[i] = tempUsrs[i+1];
		}
		userCount--;
		System.out.println("탈퇴완료. 안녕히가세요. ");
	}
	
	void printAllUser() {
		for (int i = 0; i < userCount; i++) {
			users[i].printAccount();
		}
	}
	
	Account addAccount(int identifier) {
		User currentUsr = getUser(identifier);
		// 계좌번호 중복검사
		boolean isExist = false;
		String ranAccStr = "";
		
		while(true) {
			ranAccStr = Integer.toString(ran.nextInt(1000)+1000);
			for (int i = 0; i < userCount; i++) {
				for (int j = 0; j < users[i].accCount; j++) {
					if(users[i].accs[j].number.equals(ranAccStr)) {
						isExist = true; // 똑같은게 나오면(true) 다시 
						continue;						
					}
				}
			}
			// 하나도 중복 없으면 false 나옴 -> 무한루프
			if(isExist == false) break;			
		}
		
		Account newAccount = new Account(ranAccStr, 0);
		if(currentUsr.accCount == 0) {
			currentUsr.accs = new Account[1];
		} else {
			Account[] tempAccs = currentUsr.accs;
			currentUsr.accs = new Account[currentUsr.accCount+1];
			for (int i = 0; i < tempAccs.length; i++) 
				currentUsr.accs[i] = tempAccs[i];
		}
		currentUsr.accs[currentUsr.accCount++] = newAccount;			
		return newAccount;
	}
	
	int delAccount (int identifier, String number) {
		User currentUsr = getUser(identifier);
		int existNum = -1;
		for (int i = 0; i < currentUsr.accCount; i++) {
			if(currentUsr.accs[i].number.equals(number))
				existNum = i;
		}
		if(existNum == -1) return -1;
		// 삭제진행
		Account[] tempAcc = currentUsr.accs;
		currentUsr.accs = new Account[currentUsr.accCount-1];
		int j = 0;
		for (int i = 0; i < tempAcc.length; i++) {
			if(i != existNum) {
				currentUsr.accs[j++] = tempAcc[i];
			}
		}
		currentUsr.accCount--;
		// 삭제완료 
		return currentUsr.accs[existNum].money;
	}
}
