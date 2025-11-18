for (int i = 0; i < 10; i += 2) {
	if (i % 5 == 0) {
		i++;
		continue; //skip this iteration
	}
	if (i % 8 == 0) {
		break; //end of loop
	}
	
	... //something is happening here
}