Generated with [git-fame](https://github.com/casperdcl/git-fame)

## Code and tests

```bash
$ git-fame -e -w --incl='src/main|react-parkhaus/src|src/test' --excl='src/main/webapp'                                                                                                                 

Processing: 100%|#########################################################################################################################################################| 43/43 [00:00<00:00, 134.36file/s]
Total commits: 208
Total ctimes: 1453
Total files: 83
Total loc: 4315
| Author                               |   loc |   coms |   fils |  distribution   |
|:-------------------------------------|------:|-------:|-------:|:----------------|
| emre.cetin@smail.inf.h-brs.de        |  2341 |     64 |     40 | 54.3/30.8/48.2  |
| marian.hoenscheid@smail.inf.h-brs.de |  1848 |    115 |     36 | 42.8/55.3/43.4  |
| erik.autenrieth@smail.inf.h-brs.de   |   126 |     29 |      7 | 2.9/13.9/ 8.4   |
```
## Code only
```bash
$ git-fame -e -w --incl='src/main|react-parkhaus/src' --excl='src/main/webapp'
Processing: 100%|#########################################################################################################################################################| 27/27 [00:00<00:00, 135.64file/s]
Total commits: 208
Total ctimes: 875
Total files: 52
Total loc: 2332
| Author                               |   loc |   coms |   fils |  distribution   |
|:-------------------------------------|------:|-------:|-------:|:----------------|
| emre.cetin@smail.inf.h-brs.de        |  1651 |     64 |     26 | 70.8/30.8/50.0  |
| marian.hoenscheid@smail.inf.h-brs.de |   650 |    115 |     20 | 27.9/55.3/38.5  |
| erik.autenrieth@smail.inf.h-brs.de   |    31 |     29 |      6 | 1.3/13.9/11.5   |
```
## Tests only
```bash
$ git-fame -e -w --incl='src/test'
Processing: 100%|#########################################################################################################################################################| 16/16 [00:00<00:00, 127.83file/s]
Total commits: 208
Total ctimes: 575
Total files: 31
Total loc: 1983
| Author                               |   loc |   coms |   fils |  distribution   |
|:-------------------------------------|------:|-------:|-------:|:----------------|
| marian.hoenscheid@smail.inf.h-brs.de |  1198 |    115 |     16 | 60.4/55.3/51.6  |
| emre.cetin@smail.inf.h-brs.de        |   690 |     64 |     14 | 34.8/30.8/45.2  |
| erik.autenrieth@smail.inf.h-brs.de   |    95 |     29 |      1 | 4.8/13.9/ 3.2   |
```
