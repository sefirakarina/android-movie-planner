#include <iostream>
using namespace std ;

int main (){
	
	float length,length1,width,width1,area2,area1 ;
	
	cout << "input the 1st lenght  = " ; cin>>length;
	cout <<"input the 1st width  = " ; cin>> width;
	
	cout <<endl;
	
	cout << "input the 2nd length = " ; cin>>length1;
	cout <<"input the 2nd width= " ; cin>> width1;
	cout <<endl;
	
	area1= length*width;
	area2= length1*width1;
	
	if(area1>area2)
	cout << "the 1st shape is bigger,it's area is "<<area1 <<" ,the other shape's area is " <<area2;
	
	else if(area2>area1)
	cout << "the 2nd shape is bigger,it's area is "<<area2 <<" ,the other shape's area is " <<area1;
	
	else (cout <<"both shapes's area are the same");
	
	return 0;
}
