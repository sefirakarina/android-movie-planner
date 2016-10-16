#include <iostream>
using namespace std ;

int main (){

	int month;
	int year;
	
	
	cout <<"enter a month (1-12) = ";cin>>month;
	cout<<endl;
	
	cout <<"enter a year = ";cin>>year;
	cout<<endl;
	
	
	if(month == 2 && (year%100==0)  && (year%400==0) || month == 2 && (year%4==0))
	cout<<"29 days";
	
	else if 
	(month == 2 && (year%100!=0)  && (year%400!=0) || month == 2 && (year%4!=0))
	cout<<"28 days";
	
	else if(month==1 || month==3 || month==5||month==7|| month==8|| month==10|| month==12)
	cout << "31 days";
	
	else if(month==4 || month==6 || month==9|| month==11)
	cout << "30 days";

	
	return 0;
}
