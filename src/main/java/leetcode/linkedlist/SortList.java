package leetcode.linkedlist;

import leetcode.struct.ListNode;
import leetcode.struct.ListNodeUtil;

/**
 * 给你链表的头结点 head，请将其按 升序 排列并返回 排序后的链表 。
 * <p>
 * 进阶：
 * <p>
 * 你可以在 O(nlogn) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 * <p>
 * 来源：力扣（LeetCode）
 * <p>
 * 链接：https://leetcode-cn.com/problems/sort-list
 * <p>
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * <image src="https://assets.leetcode.com/uploads/2020/09/14/sort_list_2.jpg" />
 *
 * @author dingdong
 * @since 2021/4/22
 */
public class SortList {

    public static void main(String[] args) {
        System.out.println(sortList(ListNodeUtil.toList(11, 4, 5, 2, 111)));
        System.out.println(sortList(ListNodeUtil.toList(11, 4, 5, 2, 111, 222)));
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode lHead = head;
        ListNode rHead = cutoff(head);
        ListNode lSortedHead = sortList(lHead);
        ListNode rSortedHead = sortList(rHead);
        return merge(lSortedHead, rSortedHead);
    }

    private static ListNode cutoff(ListNode head) {
        ListNode slow = new ListNode(0, head), fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode rHead = slow.next;
        slow.next = null;
        return rHead;
    }

    private static ListNode merge(ListNode lSortedHead, ListNode rSortedHead) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        ListNode tail = dummy;
        ListNode lp = lSortedHead;
        ListNode rp = rSortedHead;
        while (true) {
            if (lp != null && rp != null) {
                if (lp.val > rp.val) {
                    tail.next = rp;
                    tail = tail.next;
                    rp = rp.next;
                } else {
                    tail.next = lp;
                    tail = tail.next;
                    lp = lp.next;
                }
            } else if (lp != null) {
                tail.next = lp;
                lp = null;
            } else if (rp != null) {
                tail.next = rp;
                rp = null;
            } else {
                break;
            }
        }
        return dummy.next;
    }
}
