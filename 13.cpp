#include <iostream>
using namespace std ;

int main () {

int number ;

cout << "number of books = " ;
cin >> number;

if (number==0)
	cout << "you got 0 point";
	
else if (number==1)
	cout << "you got 5 points";
	
else if (number==2)
	cout << "you got 15 points";
	
else if (number==3)
	cout << "you got 30 points";
	
else if (number>=4)
	cout <<"you got 60 points"	;
	
else
	cout << "ERROR";
	
return 0;
}
