import java.util.Scanner;


public class primes{
	Scanner scan = new Scanner(System.in);

	public void Test(){
	while(scan.hasNext()){
	int n = scan.nextInt();
	if(n == 0){
		return;
	}
	int doubled = n*2;
	int answer = 0;

	for(int j = doubled; j < 1073741824; j++){
		if(isPrime(j)){
			answer = j;
			j = 1073741824;

		}
	}


		for(int i = 0; i < n; i++){
			if(n % i == 0){
				System.out.print(answer);
				System.out.print(" ");
				System.out.print(n);
				System.out.println(" is not prime");
			}
			else{
				System.out.println(answer);
			}
		}
	}
}

	public boolean isPrime(int n){
		for(int i = 0; i < n; i++){
			if(n % i == 0){
				return false;
			}
			else{
				return true;
			}
	}
	return false;
}

	public static void main(String[] args){
		new primes().Test();
	}

}