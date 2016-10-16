#include <iostream>
using namespace std ;

int main (){
	
int number[2];

	for (int i=1 ; i<=2 ; i++)
	{
		cout << "enter number " << i <<" = ";
		cin>>number[i];
		
	}	
	

		
	if(number[1]>number[2])
	cout << "the biggest number is "<< number[1] <<endl ;
	
	else if(number[1]<number[2])
	cout<< "the biggest number is "<< number[2];
	
	
	
			
	return 0;
}
