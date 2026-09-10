/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int matchingNodes = 0;
    
    public int averageOfSubtree(TreeNode root) {
        postOrder(root);
        return matchingNodes;
    }
    private int[] postOrder(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] leftData = postOrder(node.left);
        int[] rightData = postOrder(node.right);
        int currentSum = leftData[0] + rightData[0] + node.val;
        int currentCount = leftData[1] + rightData[1] + 1;
        if (currentSum / currentCount == node.val) {
            matchingNodes++;
        }
        return new int[]{currentSum, currentCount};   
    }
}