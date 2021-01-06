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
    
    public boolean hasEnd(ListNode start, int k) {
        if (k == 1) { return true; }
        
        // tries to jump to node (k - 1) steps away from start
        ListNode jumper = start;
        for (int i = 0; i < k - 1; i++) {
            jumper = jumper.next;
            if (jumper == null) { return false; }
        }
        return true;
    }
    
    public ListNode getEnd(ListNode start, int k) {
        if (k == 1) { return start; }
        
        // get node (k - 1) steps away from start
        ListNode jumper = start;
        for (int i = 0; i < k - 1; i++) {
            jumper = jumper.next;
        }
        return jumper;
    }
    
    // reverses linked list from start to end
    // then connects reverse list with preceeds and follows
    public void reverse(ListNode preceeds, ListNode start, ListNode end, ListNode follows) {
        if (start == end) { return; }
        
        // first iteration
        ListNode parent = start;
        ListNode child = parent.next;
        ListNode after = (child.next != null) ? child.next : null;
        child.next = parent;
        
        // do reversal
        while (child != null && child != end) {
            parent = child;
            child = (after != null) ? after : null;
            after = (child != null && child.next != null) ? child.next : null;
            child.next = parent;
        }
        
        // connect end-points
        if (preceeds != null) { preceeds.next = end; }
        start.next = (follows != null) ? follows : null;
    }
    
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1 || !this.hasEnd(head, k)) { return head; }
        
        // first iteration
        ListNode prevEnd = null;
        ListNode currStart = head;
        ListNode currEnd = this.getEnd(currStart, k);
        ListNode nextStart = (currEnd.next != null) ? currEnd.next : null;
        reverse(prevEnd, currStart, currEnd, nextStart);
        
        head = currEnd;
        
        // do reversals
        while (nextStart != null && this.hasEnd(nextStart, k)) {
            prevEnd = currStart;
            currStart = nextStart;
            currEnd = this.getEnd(currStart, k);
            nextStart = (currEnd.next != null) ? currEnd.next : null;
            reverse(prevEnd, currStart, currEnd, nextStart);
        }
                
        return head;
    }
    
}
