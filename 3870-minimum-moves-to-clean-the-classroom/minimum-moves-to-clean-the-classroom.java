import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        int k = 0;
        int startR = -1, startC = -1;
        int[][] litterMap = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'L') {
                    litterMap[i][j] = k++;
                } else if (ch == 'S') {
                    startR = i;
                    startC = j;
                }
            }
        }
        if (k == 0) return 0;
        int[][][] maxEnergy = new int[m][n][1 << k];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergy[i][j], -1);
            }
        }
        
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{startR, startC, 0, energy});
        maxEnergy[startR][startC][0] = energy;
        
        int moves = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int targetMask = (1 << k) - 1;
        
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] curr = q.poll();
                int r = curr[0];
                int c = curr[1];
                int mask = curr[2];
                int e = curr[3];
                
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                    
                    char ch = classroom[nr].charAt(nc);
                    if (ch == 'X') continue;
                    
                    int nmask = mask;
                    if (ch == 'L') {
                        nmask |= (1 << litterMap[nr][nc]);
                    }
                    
                    int ne = e - 1;
                    if (ch == 'R') {
                        ne = energy; 
                    }
                    if (nmask == targetMask) {
                        return moves + 1;
                    }
                    if (ne > 0) {
                        if (ne > maxEnergy[nr][nc][nmask]) {
                            maxEnergy[nr][nc][nmask] = ne;
                            q.add(new int[]{nr, nc, nmask, ne});
                        }
                    }
                }
            }
            moves++;
        }
        
        return -1; 
    }
}