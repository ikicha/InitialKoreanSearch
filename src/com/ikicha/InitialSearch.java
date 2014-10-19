package com.ikicha;

public class InitialSearch {
    private static char startInitial = 'ㄱ';
    private static char endInitial = 'ㅎ';
    private static char start = '가';
    private static char diff = 0x24C;

    private static int getPair(char initial) {
        return makeIntFromTwoChar(
                (char) (start + (initial - startInitial) * diff),
                (char) (start + (initial - startInitial + 1) * diff - 1));
    }

    private static int makeIntFromTwoChar(char start, char end) {
        int result = 0;
        result |= start << 16;
        result |= end;
        return result;
    }

    private static char getStart(int integer) {
        return (char) (integer >> 16);
    }

    private static char getEnd(int integer) {
        return (char) (integer & 0xFFFF);
    }

    private static CharSequence makeRegex(char inital) {
        int pair = getPair(inital);
        StringBuffer sb = new StringBuffer("[-]");
        sb.insert(2, inital);
        sb.insert(2, getEnd(pair));
        sb.insert(1, getStart(pair));
        return sb;
    }

    private static boolean determineKoreanInital(char c) {
        return c >= startInitial && c <= endInitial;
    }

    public static String makeRegexString(CharSequence query) {
        StringBuffer sb = new StringBuffer(".*");
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            if (determineKoreanInital(c)) {
                sb.append(makeRegex(c));
            } else {
                sb.append(c);
            }
        }
        sb.append(".*");
        return sb.toString();
    }

    public static void main(String[] args) {
        int pair = getPair('ㄱ');
        System.out.println(pair);
        System.out.println(getStart(pair));
        System.out.println(getEnd(pair));
        String rex = makeRegexString("하"
                + "ㄲ나");
        System.out.println(rex);
        System.out.println("하ㄲ나".matches(rex));

    }

}
