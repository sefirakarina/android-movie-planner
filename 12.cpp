#include <iostream>
using namespace std ;

int main (){
	
	int package=99;
	int number,price,discount,total;
	
	cout << "input the amount of package(s) you want = ";
	cin >> number;
	cout <<endl;
	
	if (number <= 0)
	cout << "enter only positive number";
	
	else if (9<number<20)
	{
		price = number*package;
		discount =price*0.2;
		total = price-discount;
		cout <<"total price(discount included) = " <<total;
	}
	
		else if (19<number<50)
	{
		price = number*package;
		discount =price*0.3;
		total = price-discount;
		cout <<"total price(discount included) = " <<total;
	}
	
		else if (49<number<100)
	{
		price = number*package;
		discount =price*0.4;
		total = price-discount;
		cout <<"total price(discount included) = " <<total;
	}
	
		else if (number>99)
	{
		price = number*package;
		discount =price*0.5;
		total = price-discount;
		cout <<"total price(discount included) = " <<total;
	}
	
	else if (0<number<10)
	{
		total = number*package;
		cout << "no discount , total price is = "<<total;
	}

	
	return 0;
}
