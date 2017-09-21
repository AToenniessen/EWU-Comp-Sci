#include <iostream>

using namespace std;

double readDouble(string s, int max, int min) {
    double value;
    while (true) {
        cout << s;
        if (cin >> value && cin.get() == '\n') {
            if (value <= max && value >= min)
                return value;
            printf("Value entered is outside of min/max bounds. ");
        }
        printf("Invalid input, please try again\n");
        cin.clear();
        cin.ignore(10000, '\n');
    }
}
int readInt(string s) {
    int value;
    while (true) {
        cout << s;
        if (cin >> value && cin.get() == '\n') {
            if (value >= 1)
                return value;
            printf("Value entered is less than 1. ");
        }
        printf("Invalid input, please try again\n");
        cin.clear();
        cin.ignore(10000, '\n');
    }
}
double calcAvg(double p[], int cnt) {
    double average = 0;
    for (int n = 0; n < cnt; n++) {
        average += p[n];
    }
    return average / cnt;
}
int main() {
    int students, cnt;
    double highest = 0, lowest = 100;
    students = readInt("Please enter the number of students: ");
    double points[students];
    for (cnt = 0; cnt < students; cnt++) {
        points[cnt] = readDouble("\nPlease enter student number " + to_string(cnt + 1) + "s points", 100, 0);
        if (highest < points[cnt])
            highest = points[cnt];
        if (lowest > points[cnt])
            lowest = points[cnt];
    }
    printf("\nThe highest score is %.2f\n", highest);
    printf("The lowest score is %.2f\n", lowest);
    printf("The average score is %.2f\n", calcAvg(points, cnt));
    return 0;
}