int getPay(@@cDayOfWeek@@ weekday) {
    int pay = 0;
    switch(weekday) {
        case MONDAY:
            pay = 120; // you need more on a monday
            break;     // stop fallthrough
        case SATURDAY: // saturday and sunday are the same
        case SUNDAY:   // so we fall through
            pay = 200; // bonus
            break;     // stop fallthrough
        default:
            pay = 100; // base pay
            break;
    }
    return pay;
}
