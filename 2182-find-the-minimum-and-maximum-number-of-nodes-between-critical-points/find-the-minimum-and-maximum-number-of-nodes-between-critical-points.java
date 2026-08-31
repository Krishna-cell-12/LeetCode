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
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int firstIdx = -1;
        int lastIdx = -1;
        int minDistance = Integer.MAX_VALUE;
        int idx = 1;

        ListNode previous = head;
        ListNode current = head.next;

        while(current.next != null){
            boolean isMaxima = current.val > previous.val && current.val > current.next.val;
            boolean isMinima = current.val < previous.val && current.val < current.next.val;

            if (isMaxima || isMinima) {
                if (lastIdx != -1) {
                    minDistance = Math.min(minDistance, idx - lastIdx);
                }
                if (firstIdx == -1) {
                    firstIdx = idx;
                }
                lastIdx = idx;
            }

            previous = current;
            current = current.next;
            idx++;
        }
         if (firstIdx == -1 || firstIdx == lastIdx) {
            return new int[]{-1, -1};
        }

        int maxDistance = lastIdx - firstIdx;
        return new int[]{minDistance, maxDistance};
    }
}