/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class Solution {
    
    public boolean isAllProcessed(ListNode[] lists) {
        for (ListNode node : lists) {
            if (node != null) {
                return false;
            }
        }
        return true;
    } 
    
    public ListNode findSmallestAndRemove(ListNode[] lists) {
        if (isAllProcessed(lists)) {
            return null;
        } else {
            int valOfSmallest = Integer.MAX_VALUE;
            int indexOfSmallest = 0;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && lists[i].val < valOfSmallest) {
                    valOfSmallest = lists[i].val;
                    indexOfSmallest = i;
                }
            }
            ListNode smallest = lists[indexOfSmallest];
            if (smallest.next == null) {
                lists[indexOfSmallest] = null;
            } else {
                ListNode next = smallest.next;
                lists[indexOfSmallest] = next;
            }
            return smallest;
        }
    }
    
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode smallest = findSmallestAndRemove(lists);
        if (smallest != null) {
            smallest.next = mergeKLists(lists);
        }
        return smallest;
    }
}
