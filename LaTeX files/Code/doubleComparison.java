double x;
double y;

if (x == y) { //sehr schlecht, kommt fast nie durch
	//...
}

double epsilon = 0.001; //gewuenschte Genauigkeit
boolean result = Math.abs(x - y) < epsilon; //viel besser!