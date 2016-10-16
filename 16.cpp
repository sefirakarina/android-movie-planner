#include <iostream>
#include <string>
using namespace std ;

int main ()
{
	string name[3];
	int time [3];
	
	for (int i=0 ; i<3 ; i++)
	{
		cout << "enter name " << i+1 <<" = ";
		cin>>name[i];
	}
	
	cout<<endl;
	
	for (int i=0 ; i<3 ; i++)
	{
		cout << "enter time "<<i+1<<" = ";
		cin>>time[i];
	}
	

		if(time[0]<0||time[1]<0||time[2]<0)
		cout <<"error";
		
		else if (time[0]<time[1]<time[2])
		cout << name[0]<<","<<name[1]<<","<<name[2];
	
		else if (time[0]<time[2]<time[1])
		cout << name[0]<<","<<name[2]<<","<<name[1];
		
		else if (time[1]<time[0]<time[2])
		cout << name[1]<<","<<name[0]<<","<<name[2];
		
		else if (time[1]<time[2]<time[0])
		cout << name[1]<<","<<name[2]<<","<<name[0];
		
		else if (time[2]<time[1]<time[0])
		cout << name[2]<<","<<name[1]<<","<<name[0];
		
		else if (time[2]<time[0]<time[1])
		cout << name[2]<<","<<name[0]<<","<<name[1];
	
	
return 0;	
}
