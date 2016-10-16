#include <iostream>
using namespace std ; 

int main (){
	
	int year,month,date;
	
	cout <<"input date               = " ; cin>>date;cout<<endl;
	cout <<"input month (in numeric) = " ; cin>>month;cout<<endl;
	cout <<"input 2 digits year      = " ; cin>>year;cout<<endl;
	
	if (date*month==year)
	cout << date <<"/"<<month<<"/" <<year<<" "<<"is a magic date >_<";
	
	else 
	cout << date <<"/"<<month<<"/" <<year<<" "<<"is not a magic date :(";
	
	return 0;
}
