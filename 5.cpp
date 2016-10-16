#include <iostream>
using namespace std ; 

int main (){
	
	float weight,height,bmi;
	
	cout <<"input your height = "; cin>>height;cout<<endl;
	cout <<"input your weight = "; cin>>weight;cout<<endl;
	
	bmi=weight*703/(height*height);
	
	
	if (18.5<=bmi<=25)
	cout<<"your body is optimal";
	
	else if (bmi<18.5)
	cout<<"you are underweight,get a sandwich";
	
	else if(bmi>25)
	cout <<"you are overweight,get in shape";
	
	
	return 0; 
}
