//Viet ham tim hai so nguyen duong hai chu so thoa man boi chung nho nhi gap M lan uoc chung lon nhi
#include <iostream>
using namespace std;
//Viet ham tim boi chung nho nhi cua 2 so a,b
int BCNNhi(int a, int b)
{
	int k = 0, i = 1, boichung = a > b ? a : b, kq;

	while (k < 2)
	{
		kq = boichung * i;
		if (kq % a == 0 && kq % b == 0)
			k++;
		i++;
	}
	return kq;
}
//Viet ham tim uoc chung lon nhi cau 2 so a, b
int UCLNhi(int a, int b)
{
	int k = 0, uocchung = a > b ? b : a, uclnhi;
	while (k < 2)
	{
		if (a % uocchung == 0 && b % uocchung == 0)
		{
			k++;
			uclnhi = uocchung;
		}
		uocchung--;
		if (k == 1 && uocchung == 0)
		{
			uclnhi = 0;
			break;
		}
	}
	return uclnhi;
}
//Viet ham tim hai so nguyen duong hai chu so thoa man boi chung nho nhi gap M lan uoc chung lon nhi
int Tim_2_so(int M, int& k, int& a, int& b)
{
	a = 10;
	while (a < 100)
	{
		b = 10;
		while (b < 100)
		{
			if (BCNNhi(a, b) == UCLNhi(a, b) * M)
			{
				k = 1;
				return 0;
			}
			b++;
		}
		a++;
	}
}
int main()
{
	int a, b, M, k = 0;
	cout << "a= "; cin >> a;
	cout << "b= "; cin >> b;
	cout << BCNNhi(a, b) << endl;
	if (UCLNhi(a, b) == 0)
		cout << "khong co uoc chung duong lon nhi.";
	else cout << "Uoc chung lon nhi = " << UCLNhi(a, b);
	cout << endl << "M= ";
	cin >> M;
	Tim_2_so(M, k, a, b);
	if (k == 0)
		cout << "khong co cap so a, b thoa de bai.";
	else
	{
		cout << "a= " << a << endl;
		cout << "b= " << b << endl;
	}
	return 0;
}