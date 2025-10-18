package com.manager;

import java.util.List;
import java.util.Scanner;

public class PaginationUtil {
    
    public static <T> void paginate(List<T> items, String title, int pageSize, Scanner scanner) {
        if (items.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }
        
        int totalPages = (int) Math.ceil((double) items.size() / pageSize);
        int currentPage = 0;
        
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            System.out.println("=== " + title + " ===");
            System.out.println("Page " + (currentPage + 1) + " of " + totalPages);
            System.out.println("=" .repeat(50));
            
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, items.size());
            
            for (int i = start; i < end; i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
            
            System.out.println("=" .repeat(50));
            System.out.println("Navigation: [N]ext | [P]revious | [B]ack to menu");
            
            // Special cases for first and last pages
            if (currentPage == 0 && totalPages == 1) {
                System.out.print("Press [B] to go back: ");
            } else if (currentPage == 0) {
                System.out.print("Press [N]ext or [B]ack: ");
            } else if (currentPage == totalPages - 1) {
                System.out.print("Press [P]revious or [B]ack: ");
            } else {
                System.out.print("Press [N]ext, [P]revious or [B]ack: ");
            }
            
            String input = scanner.nextLine().trim().toLowerCase();
            
            switch (input) {
                case "n":
                case "next":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        System.out.println("You're on the last page!");
                        waitForEnter(scanner);
                    }
                    break;
                    
                case "p":
                case "previous":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("You're on the first page!");
                        waitForEnter(scanner);
                    }
                    break;
                    
                case "b":
                case "back":
                    return;
                    
                default:
                    System.out.println("Invalid option! Use N, P, or B.");
                    waitForEnter(scanner);
            }
        }
    }
    
    private static void waitForEnter(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}