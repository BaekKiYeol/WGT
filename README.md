# WGT (Where are you going today? / 오늘 어디가?)
+ 매장 웨이팅 등록 및 소규모 SNS 서비스 제공 사이트
+ 2022.8.8 ~ 2022.8.27
+ 모바일 인터페이스
## 팀 구성
+ 총 5명 ( [본인](https://github.com/BaekKiYeol) [이성호](https://github.com/LeeSeongHo7984) [이태영](https://github.com/wed456) [박상규](https://github.com/parkSangGyu98) [한태우](https://github.com/workhan0918))
## 사용한 기술 및 환경
+ Window
+ eclipse
+ HTML/CSS3
+ Java
+ Spring
+ Jsp
+ MySQL
+ Javascript
+ bootstrap

## 구현 기능
+ 사용자
  + 로그인, 로그아웃, 회원가입, 정보수정, 탈퇴
  + 매장정보 조회 및 검색
  + 매장위치 확인 (카카오지도API)
  + 웨이팅 조회 및 등록
  + SNS 게시글 조회, 등록, 수정, 삭제, 좋아요 기능
+ 매장 점주
  + 로그인, 로그아웃, 회원가입
  + 매장 등록, 메뉴 등록
  + 게시글 삭제
  
## 담당 기능
+ 게시판
  + 게시글 작성, 조회, 수정, 삭제, 검색
  + 좋아요 기능
  
## 주요 코드
### 사용자 부분
 + 매장정보조회 및 검색
    1. ID값을 이용하여 매장 정보들을 가져와 배열에 담아서 마커에 추가
    2. 카카오지도API를 이용하여 마커를 지도에 표시
    3. 검색란에 매장명 입력 시 검색한 단어를 포함하는 모든 매장 표시
    
              map.jsp 스크립트 일부
              
              for (var i = 0, len = count; i < len; i++) {
              var gapX = (MARKER_WIDTH), // 스프라이트 이미지에서 마커로 사용할 이미지 X좌표 간격 값
              originY = (MARKER_HEIGHT) * i, // 스프라이트 이미지에서 기본, 클릭 마커로 사용할 Y좌표 값
              overOriginY = (OVER_MARKER_HEIGHT) * i, // 스프라이트 이미지에서 오버 마커로 사용할 Y좌표 값
              normalOrigin = new kakao.maps.Point(0, originY), // 스프라이트 이미지에서 기본 마커로 사용할 영역의 좌상단 좌표
              clickOrigin = new kakao.maps.Point(gapX, originY), // 스프라이트 이미지에서 마우스오버 마커로 사용할 영역의 좌상단 좌표
              overOrigin = new kakao.maps.Point(gapX * 2, overOriginY); // 스프라이트 이미지에서 클릭 마커로 사용할 영역의 좌상단 좌표

              positions.push(new kakao.maps.LatLng(document
              .getElementById("longitude" + i).value, document
              .getElementById("latitude" + i).value)); //좌표값을 받아와 배열에 추가하여 마커를 표시

              content.push('<div class="wrap"><div class="info"><div class="title">'
               + document.getElementById("findname" + i).value
               + '</div></div></div>');//가게이름을 받아와 배열에 추가

              inputText.push(document.getElementById("findname" + i).value)

                        // 마커를 생성하고 지도위에 표시합니다
                        addMarker(positions[i], inputText[i], content[i],
                            normalOrigin, overOrigin, clickOrigin);
                        }
                        // 마커를 생성하고 지도 위에 표시하고, 마커에 mouseover, mouseout, click 이벤트를 등록하는 함수입니다
                        function addMarker(position, inputText, content,
                            normalOrigin, overOrigin, clickOrigin) {

                        var markerImage = new kakao.maps.MarkerImage(imageSrc,
                            markerSize), overMarker = new kakao.maps.MarkerImage(
                            imageSrc, overMarkerSize), clickMarker = new kakao.maps.MarkerImage(
                            imageSrc, clickMarkerSize);

                        // 마커를 생성하고 이미지는 기본 마커 이미지를 사용합니다
                        var marker = new kakao.maps.Marker({
                          map : map,
                          position : position,
                          image : markerImage
                        });

                        var overlay = new kakao.maps.CustomOverlay({
                          content : content,
                          map : map,
                          position : position
                        });

                        marker.markerImage = markerImage;
                        // 마커에 click 이벤트를 등록합니다
                        overlay.setMap(null);
                        kakao.maps.event
                            .addListener(
                                marker,
                        'click',
                        function() {
                          // 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
                          // 마커의 이미지를 클릭 이미지로 변경합니다
                          for (var i = 0, len = count; i < len; i++) {
                            document.getElementById("inputSearch").value = inputText
                          }

                          if (!selectedMarker
                              || selectedMarker !== marker) {
                            // 클릭된 마커 객체가 null이 아니면
                            // 클릭된 마커의 이미지를 기본 이미지로 변경하고
                            !!selectedMarker
                                && selectedMarker
                                    .setImage(selectedMarker.markerImage);
                            !!selectedContent
                                && selectedContent.setMap(null);
                          }
                          filter()

                          // 현재 클릭된 마커의 이미지는 클릭 이미지로 변경, 컨테츠를 띄워줌
                          if (marker.markerImage != clickMarker) {
                            marker.setImage(clickMarker)
                            overlay.setMap(map)

                            }

                            // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
                            selectedMarker = marker;
                            selectedContent = overlay;

                          });

                            kakao.maps.event.addListener(map, 'click', function() {
                              if (!overlay.setMap(null)) {
                                overlay.setMap(null);
                                marker.setImage(markerImage)
                              }
                            })
                          }


                          MapDao 일부

                          public List<Map> search(String name){
                          String sql = "SELECT * FROM test WHERE name like '%" + name +"%' ";
                          return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Map>(Map.class));
                          }              

  4. 매장을 검색시 그 매장의 메뉴도 불러와서 조회할 수 있도록 하는 과정에서 다소 어려웠지만 스크립트에 배열과 for문을 이용하여 해결
  
                Map.jsp 일부

                <c:forEach var="menu" items="${menuList}" varStatus="status">
                  <c:forEach var="x" items="${menu}" varStatus="t">
                    <div class="menulist${status.index}"
                      id="findmenu${status.index}${t.index}" style="display: none;">
                        <span class="menu">${menuList[status.index][t.index].menuName}: </span>
                        <span class="menu">${menuList[status.index][t.index].menuPrice}</span>
                    </div>
                  </c:forEach>
                </c:forEach>

                Map.jsp 스크립트 일부

                function filter() {

                var value, name, item, i, background, menuList, menu;

                value = document.getElementById("inputSearch").value.toUpperCase();
                item = document.getElementsByClassName("item");
                background = document.getElementById("menu_wrap")
                menuList = []

                for (i = 0; i < item.length; i++) {
                  menuList.push(document.getElementsByClassName("menulist" + i));
                  name = item[i].getElementsByClassName("name")
                  for (j = 0; j < menuList[i].length; j++) {
                    menu = menuList[i][j].getElementsByClassName("menu")
                    if (name[0].innerHTML.toUpperCase().indexOf(value) > -1) {
                      item[i].style.display = "block";
                      menuList[i][j].style.display = "block";
                      background.style.opacity = "100";
                      background.style.left = "0";
                    } else {
                      item[i].style.display = "none";
                      menuList[i][j].style.display = "none";
                    }

                    if (value.length == 0) {
                      menuList[i][j].style.display = "none";
                      item[i].style.display = "none";
                      background.style.opacity = "0";
                      background.style.left = "-270px";
                    }
                  }
                  }
                }


+ 웨이팅 조회 및 등록
  1. 매장명과 로그인되어있는 ID 세션을 이용하여 정보 이용
  2. 순서나 내 앞의 팀들은 DB에 저장된 regDate를 비교하여 계산
  3. 웨이팅 첫번째 순서일 때 자동으로 웨이팅 취소를 시키기 위해 먼저 현재시간에서 5분뒤의 시간을 DB에 저장
  4. DB에 저장한 시간과 현재시간을 비교해 현재시간이 더 크다면 웨이팅을 삭제
  
                 WaitingController 일부

                 @GetMapping("/controller/get_waiting")
                 public String getWaitingForm(Model model, HttpSession session) throws ParseException {
                 // 웨이팅을 하지 않았을 때
                 if (waitingService.findWaitingById((String) session.getAttribute("userId")).get(0).getBarName() == "없음") {
                   List<Waiting> noWaiting = waitingService.findWaitingById((String) session.getAttribute("userId"));
                   model.addAttribute("frontCount", "0");
                   model.addAttribute("allCount", "0");
                   model.addAttribute("waiting", noWaiting);
                   model.addAttribute("shopTel", "-");
                   return "waiting/get_waiting";
                 }

                 // 웨이팅 해둔 상태 일때
                 List<Waiting> waitingList = waitingService.findAllWaiting(
                     waitingService.findWaitingById((String) session.getAttribute("userId")).get(0).getBarName());
                 long allCount = 0;
                 long frontCount = 0;

                 try {
                   Date day1;
                   Date day2;
                   day2 = format
                       .parse(waitingService.findWaitingById((String) session.getAttribute("userId")).get(0).getRegDate());
                   for (int i = 0; i < waitingList.size(); i++) {
                     allCount++; // 특정 매장에대한 나 포함 모든 웨이팅 수
                     day1 = format.parse(waitingList.get(i).getRegDate());
                     int compare = day1.compareTo(day2);
                     if (compare < 0) {
                       frontCount++; // 내 앞의 웨이팅 수
                     }
                   }
                 } catch (ParseException e) {
                   e.printStackTrace();
                 }

                 model.addAttribute("frontCount", frontCount);
                 model.addAttribute("allCount", allCount);
                 model.addAttribute("waiting", waitingService.findWaitingById((String) session.getAttribute("userId")));
                 model.addAttribute("shopTel",
                     shopService.findAllByShopName(
                         waitingService.findWaitingById((String) session.getAttribute("userId")).get(0).getBarName()).get(0).getShopTel());
                 // 내 앞 대기팀이 0팀 일때
                 if (frontCount == 0) {
                   // 언제까지오라는 시간 부여받지 않았을때 or waitingStartTime이 0 일때
                   if (waitingService.findWaitingById((String) session.getAttribute("userId")).get(0).getWaitingStartTime()
                       .equals("0")) {
                     SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
                     Date nowDate = new Date();
                     Calendar cal = Calendar.getInstance();
                     cal.setTime(nowDate);
                     cal.add(Calendar.MINUTE, 1);
                     String outputText = outputFormat.format(cal.getTime());

                     waitingService.addWaitingTime((String) session.getAttribute("userId"), outputText);
                     String waitingTime = waitingService.findWaitingById((String) session.getAttribute("userId")).get(0)
                         .getWaitingStartTime();
                     model.addAttribute("msg", waitingTime + " 까지 와주시기 바랍니다. (자동취소 예정)");
                     return "waiting/get_waiting";
                   } else { 
                     // 언제까지 오라는 시간을 부여 받은 상황
                     SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                     Date nowDate = new Date();

                     String waitingTime = waitingService.findWaitingById((String) session.getAttribute("userId")).get(0)
                         .getWaitingStartTime();

                     System.out.println(waitingTime);
                     System.out.println(formatter.parse(waitingTime));
                     //				String now = formatter.format(nowDate);
                     //				System.out.println(formatter.parse(waitingTime).after(now));

                     model.addAttribute("msg", waitingTime + " 까지 와주시기 바랍니다. (자동취소 예정)");

                     System.out.println(nowDate.after(formatter.parse(waitingTime)));
                     // 현재시간과 웨이팅타임을 비교하여 현재시간이 더 늦으면 웨이팅을 지우기
                     if ( nowDate.after(formatter.parse(waitingTime)) ) { // formatter.parse(waitingTime).after(nowDate)
                       waitingService.deleteWaiting((String) session.getAttribute("userId"));
                       return "waiting/get_waiting";
                     }
                      }
                     }
                     return "waiting/get_waiting";
                      }  
  
+ 게시글 좋아요 기능
  1. DB에 좋아요 테이블을 만들고 눌려졌는지 체크하는 likeCheck 열 만들어 true, false로 구분
  2. 좋아요 클릭 시 false 라면 true 로 바꿔줌과 동시에 게시글 총 좋아요 개수를 1 더하여 업데이트
  3. A 아이디로 좋아요를 눌러둔 상태에서 B가 로그인 했을 때, 좋아요가 눌러져있는 상황 발생
  4. 우선, controller에서 Like테이블의 likeCheck를 이용하여 for문과 if문을 사용해서 false인지 true인지 확인
  5. false라면 게시글의 좋아요 이미지 모두를 흰색하트로 변경하는 작업을 추가하면서 해결(true일 경우 반대로 빨간하트 추가)
 
  
                BoardController 일부

                @GetMapping("/board/home")
                public String list(HttpSession session, Model model, Board board) {
                  String userId = (String) session.getAttribute("userId");
                  if (session.getAttribute("userId") == null) {
                    model.addAttribute("msg", "로그인이 필요한 서비스 입니다.");
                    model.addAttribute("url", "../login");
                    return "alert/alert";
                  }

                  for (int i = 0; i < service.read(board).size(); i++) {
                    long boardNum = service.read(board).get(i).getNumber();

                    if ( service.findLikes(userId, boardNum).get(0).getLikeCheck().equals("false") ) {
                      service.updateLikeImg(boardNum, "dislikeheart");
                    }else {
                      service.updateLikeImg(boardNum, "likeheart");
                    }
                  }

                  model.addAttribute("board", service.read(board));

                  return "board/home";
                }

                @GetMapping("/board/likes")
                public String getLikes(HttpSession session, Model model, Board board) {
                  String userId = (String) session.getAttribute("userId");

                  if (session.getAttribute("userId") == null) {
                    model.addAttribute("msg", "로그인이 필요한 서비스 입니다.");
                    model.addAttribute("url", "../login");
                    return "alert/alert";
                  }


                  // 만약 Likes 테이블에 id, number가 동일한 정보가 없으면 만들어주기 아니면 밑에꺼 실행 
                  if ( service.findLikes(userId, board.getNumber()).get(0).getUserId().equals("없음")
                      && service.findLikes(userId, board.getNumber()).get(0).getNumber() == -1 ) {
                    Likes likes = new Likes();
                    String result = "false";
                    likes.setUserId(userId);
                    likes.setLikeCheck(result);
                    likes.setNumber(board.getNumber());
                    service.likeuser(likes);
                    service.checkUpdate(userId, board.getNumber(), "true");
                    service.likecountPlus(board.getLikecount(), board.getNumber());
                    service.updateLikeImg(board.getNumber(), "likeheart");
                  }else { // DB에 아이디랑 게시글번호가 동일한 정보가 있다면 true, false를 비교한다
                    if(service.findLikes(userId, board.getNumber()).get(0).getLikeCheck().equals("false")) { // 좋아요를 누르지 않은 상태태
                      service.checkUpdate(userId, board.getNumber(), "true");
                      service.likecountPlus(board.getLikecount(), board.getNumber());
                      service.updateLikeImg(board.getNumber(), "likeheart");
                    }else {
                      service.checkUpdate(userId, board.getNumber(), "false");
                      service.likecountDown(board.getLikecount(), board.getNumber());
                      service.updateLikeImg(board.getNumber(), "dislikeheart");
                    }
                  }

                  return "redirect:/board/home";
                }
                            

+ 게시판 수정 기능
  1. 유저와 점주의 입장이 다르기 때문에 if문을 사용하여 jsp에 c:if문으로 전달
  
                BoardController 일부
		
  		@GetMapping("/board/update")
		  public String updateget(@RequestParam("number") int number, MultipartFile file, HttpServletRequest request,
			HttpSession session, Model model, Board board) {
		  String userId = (String) session.getAttribute("userId");
		  String bnsNum = (String) session.getAttribute("bnsNum");
		  model.addAttribute("shop", service2.findShopByBnsNum(bnsNum).getShopName());
		  model.addAttribute("board", service.searchByNumber(number));
		  return "board/update";
		  }
  
  + 게시판 삭제 기능
  1. 게시글 작성시 고유 번호를 같이 입력하여 삭제시 그 번호 게시글만 삭제되게 실행
  
                BoardController 일부
  
  		@GetMapping("/board/delete")
		public String deleteGet(@RequestParam("number") int number, Model model, HttpSession session, Board board) {
		  board = service.searchByNumber(number);
		  String imgname = board.getImgname(); // board 선언, imgname-board 객체변환

		  String filePath = "C:\\wgt\\Where-are-you-going-today\\wgt\\src\\main\\webapp\\resources\\board\\img\\upload"
				+ imgname + ".jpg";
		  File file = new File(filePath);
		  // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
		  if (file.exists()) {
			// 파일을 삭제합니다.
			session.removeAttribute(imgname);
			file.delete();
			System.out.println("파일을 삭제하였습니다.");
		  } else {
			System.out.println("파일이 존재하지 않습니다.");
		  }
		  service.delete(number, imgname);

		  boolean ownerchk = false;
		  if (session.getAttribute("dbOwner") == null) {
			model.addAttribute("ownerchk", ownerchk);
			return "redirect:/board/mypage";
		  } else {
			model.addAttribute("ownerchk", ownerchk);
			return "redirect:/board/home";
		  	}
		  }
  
    + 게시판 검색 기능
    1. 게시글 검색시 조회된 게시글이 없으면 오류 발생
    2. 그래서 dao부분에서 try catch 구문으로 오류 해결
    
                BoardController 일부
		
		@GetMapping("/board/search")
		public String searchlist(HttpSession session, Model model) {
			return "board/search";
		}

		@PostMapping("/board/search")
		public String getsearchlist(Board board, Model model, HttpSession session) {
			String bnsNum = (String) session.getAttribute("bnsNum");
			List<Board> list = service.search(board.getTitle(), bnsNum);		
			model.addAttribute("list", list);	
			return "board/search";
		}
	
    
+ 게시판 검색 기능(dao 부분)

		BoardDao 일부
	
		public List<Board> search(String title, String bnsNum) {
			String sql = "SELECT * FROM Board WHERE title like '%" + title + "%' AND businessNumber = ? ORDER BY regDate DESC";
			try {
				jdbcTemplate.query(sql, new BeanPropertyRowMapper<Board>(Board.class), bnsNum);
			}catch(EmptyResultDataAccessException e){
				e.printStackTrace();
			}
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Board>(Board.class), bnsNum);
		}
  
                            

## 구현 화면
  ### 로그인
  
  ![image](https://user-images.githubusercontent.com/103983349/185832375-9707c544-f180-4498-b88a-5555079e6bca.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832405-d0fbac5f-4523-4a01-9d6d-79e1f89ad091.png)

  ### 사용자 회원가입
    + 우편번호 찾기는 API를 이용하였습니다.
    
  ![image](https://user-images.githubusercontent.com/103983349/185832447-31edfd86-0ddb-49ff-ac04-99319711f116.png)
  
  ### 점주 회원가입 및 매장 등록
  
  ![image](https://user-images.githubusercontent.com/103983349/185832474-b2ca2435-94eb-4513-b53b-ee2f323cc32a.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832628-fb1b6133-6b11-45df-b7f5-709f252d7018.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832648-05268b9c-9855-4e52-a8b6-20e91609fc1f.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832732-acb66a6c-6ee6-45a5-b16b-eddbd57a9f40.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832846-88e2738a-e6f9-4726-b1b1-fa768da9e85f.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832917-53ef22d6-9ed5-414c-a0d6-7bf026f4f2b5.png)
  ![image](https://user-images.githubusercontent.com/103983349/185832956-02f4a112-6e66-41f5-bc22-57131b28bdd2.png)
  ![image](https://user-images.githubusercontent.com/103983349/185833147-730194d5-bb5d-4901-9294-4caf0691f49d.png)
  ![image](https://user-images.githubusercontent.com/103983349/185833159-4fe5325a-27a3-41b0-a3a2-6f96a642360c.png)
  
  ### 메인 화면
  
  ![image](https://user-images.githubusercontent.com/103983364/188773736-0277e635-80ec-4be6-890a-2e085b4fbb13.png)
  
  ### 우측상단 버튼
  
  ![image](https://user-images.githubusercontent.com/103983349/186112422-b8f3f89d-5da9-4167-8df3-a1bf908ff770.png)
  
  ### 나의 웨이팅 내역
  
  ![image](https://user-images.githubusercontent.com/103983349/186304775-e35b4108-f1b4-476f-b8d7-5b34edbf2798.png)
  
  ### 회원 정보 열람 및 수정
  
  ![image](https://user-images.githubusercontent.com/103983349/185879805-9176988a-07de-4d6a-9205-e67152bca744.png)
  
  ### 검색
  
  ![image](https://user-images.githubusercontent.com/103983349/186111419-0bd5895d-d4ba-4452-8629-879e292affb6.png)
  
  ### 웨이팅 등록
  
  ![image](https://user-images.githubusercontent.com/103983349/185833842-4e75efb3-75b5-4308-b070-e2d3b7552772.png)
  
  ### SNS
  
  ![image](https://user-images.githubusercontent.com/103983364/188776820-620d0518-b83d-4a8e-81b2-3a0ae24bb16f.png)
  ![image](https://user-images.githubusercontent.com/103983364/188775981-da5ccbb8-7c44-42a9-aa03-eaaa5de22890.png)

  
  ### 게시글 작성
  
  ![image](https://user-images.githubusercontent.com/103983364/188776885-c5d11624-6a14-4895-af54-5f047065a475.png)
  
  ### 게시글 수정
  
  ![image](https://user-images.githubusercontent.com/103983364/188776974-d25b8edf-a54d-4db4-b953-788d647c4c2b.png)
  ![image](https://user-images.githubusercontent.com/103983364/188777031-8fb6b24b-a273-46a2-9c8f-f742957896df.png)

  ### 게시글 검색
  ![image](https://user-images.githubusercontent.com/103983364/188777198-0070106e-865d-4380-8d1f-f0fc26311c5c.png)
  ![image](https://user-images.githubusercontent.com/103983364/188777273-35dfdc6c-1050-4eb5-8986-d6e0c90b5ddd.png)


