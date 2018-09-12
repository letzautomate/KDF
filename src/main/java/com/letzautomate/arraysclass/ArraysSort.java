package com.letzautomate.arraysclass;

import java.util.Arrays;

public class ArraysSort {

	
			public static void main(String[] args) {
				
				int[] a = {1,2,3,8,3,5};
				Arrays.sort(a);
				for(int i: a) {
					System.out.println(i); //{1,2,3,8,3,5}
				}
				
				String[] b = {"Z", "B", "X", "A"};
				Arrays.sort(b);
				for(String s : b) {
					System.out.println(s); //"A", "B", "X", "Z"
				}
				
			}
}
