package com.karafzar.payroll1402.util;

import java.util.StringTokenizer;

public class DigitUtil
{
    // manage thousands separator //250,000
    public String thousandDigitSeparator(String digit)
    {
        try
        {
            StringTokenizer stringTokenizer = new StringTokenizer(digit, ".");
            String string = "";
            int i = 0;
            StringBuilder stringBuilder = new StringBuilder();

            if (stringTokenizer.countTokens() > 1)
            {
                digit = stringTokenizer.nextToken();
                string = stringTokenizer.nextToken();
            }

            int j = -1 + digit.length();

            if (digit.charAt(-1 + digit.length()) == '.')
            {
                j--;
                stringBuilder = new StringBuilder(".");
            }

            for (int k = j;; k--)
            {
                if (k < 0)
                {
                    if (string.length() > 0)
                        stringBuilder.append(".").append(string);

                    return stringBuilder.toString();
                }

                if (i == 3)
                {
                    stringBuilder.insert(0, ",");
                    i = 0;
                }

                stringBuilder.insert(0, digit.charAt(k));
                i++;
            }
        }
        catch (Exception ignored) {}

        return null;
    }
}