#include <iostream>
#include <string>
#include <cstdlib> // random
#include <ctime> //time
using namespace std ;

int main (){
	
	unsigned long seed = time (0) ;  
	srand(seed);
	
	int randomnumber = rand();
	int randomnumber1 = rand();
	int answer = randomnumber1 + randomnumber ;
	float number ; 
	
	cout <<randomnumber1 << " + " <<randomnumber << " = ";
	cin>> number;
	cout <<endl;
	
	
	if (number==answer)
	{
		cout <<"correct!!";
	} 
	else 
	{
		cout <<"OMG NO,the right answer is " <<answer ;
	}
	
	return 0;	
}
