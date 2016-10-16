#include <iostream>
#include <string>
using namespace std;

int main (){

	int color1,color2;
	
	
		cout << "1.red"<<endl
		<<"2.blue"<<endl
		<<"3.yellow"<<endl<<endl;
		
	
	cout <<"input choice 1 (1-3) = "; cin>>color1;
	cout <<"input choice 2 (1-3) = "; cin>>color2;
	
	cout<<endl;
	
	if (color1==1 && color2==2 || color2==2 && color1==1)
		{
			cout<<"purple";
		}

	else if (color1==1 && color2==3 || color2==1 && color1==3)
		{
			cout<<"orange";
		}
		
		
	else if (color1==3 && color2==2 || color2==3 && color1==2)
		{
			cout <<"green";
		}
		
	else
	cout<<"error";
	
		
	return 0;
}
