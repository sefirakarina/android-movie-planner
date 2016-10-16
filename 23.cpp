#include <iostream>
using namespace std;

int main(){
	
	int choice;
	float area;
	
	cout << "1.calculate circle's area"<<endl
		<<"2.calculate rectangle's area"<<endl
		<<"3.calculate triangle's area"<<endl
		<<"4.quit?"<<endl<<endl
		<<"enter your choice(1-4) = ";
		
	cin>> choice; cout<<endl;
	
	if (choice==4)
	{
		cout<<"okay,just PRESS ENTER and then bye";
	}
	
	else if (choice==1)
	{
		float radius;
		
		cout <<"enter radius = " ; 
		cin>>radius;
		
		area=3.14159*radius*radius;
		
		cout<<endl;
		cout << "area = "<<area;
	}
	
	else if (choice=2)
	{
		float length,width; 
		
		cout<<"enter length = " ; cin>>length;
		cout<<"enter width = " ; cin>>width;
		
		area = length*width;
		
		cout<<endl;
		cout << "area = "<<area;
	}
	
		else if (choice=3)
	{
		float base,height; 
		
		cout<<"enter base = " ; cin>>base;
		cout<<"enter height = " ; cin>>height;
		
		area = base*height/2;
		
		cout<<endl;
		cout << "area = "<<area;
	}
	
	else 
	cout<<"error";

	
	return 0;
}
