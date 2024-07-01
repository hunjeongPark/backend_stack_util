//다중 멀티쓰레드 호출 방식 - start(쓰레드 시작) / join(한 쓰레드의 끝맺음까지 waiting) / interrupt(끝난 쓰레드 정리)
try {
  switch (threadCount) {
      case 1 :
          threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth));

          break;
      case 3 :
          Thread thQ1 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth)));
          Thread thQ2 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 1)));
          Thread thQ3 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 2)));

          thQ1.start();
          thQ2.start();
          thQ3.start();

          thQ1.join();
          thQ2.join();
          thQ3.join();

          thQ1.interrupt();
          thQ2.interrupt();
          thQ3.interrupt();

          break;
      case 6 :
          Thread thH1 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth)));
          Thread thH2 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 1)));
          Thread thH3 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 2)));
          Thread thH4 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 3)));
          Thread thH5 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 4)));
          Thread thH6 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth + 5)));

          thH1.start();
          thH2.start();
          thH3.start();
          thH4.start();
          thH5.start();
          thH6.start();

          thH1.join();
          thH2.join();
          thH3.join();
          thH4.join();
          thH5.join();
          thH6.join();

          thH1.interrupt();
          thH2.interrupt();
          thH3.interrupt();
          thH4.interrupt();
          thH5.interrupt();
          thH6.interrupt();

          break;
      case 12 :
          Thread thY1 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth)));
          Thread thY2 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+1)));
          Thread thY3 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+2)));
          Thread thY4 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+3)));
          Thread thY5 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+4)));
          Thread thY6 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+5)));
          Thread thY7 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+6)));
          Thread thY8 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+7)));
          Thread thY9 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+8)));
          Thread thY10 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+9)));
          Thread thY11 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+10)));
          Thread thY12 = new Thread(() -> threadMultiValueList.add(airUnitPriceThreadSelectList(param, plusMonth+11)));

          thY1.start();
          thY2.start();
          thY3.start();
          thY4.start();
          thY5.start();
          thY6.start();
          thY7.start();
          thY8.start();
          thY9.start();
          thY10.start();
          thY11.start();
          thY12.start();

          thY1.join();
          thY2.join();
          thY3.join();
          thY4.join();
          thY5.join();
          thY6.join();
          thY7.join();
          thY8.join();
          thY9.join();
          thY10.join();
          thY11.join();
          thY12.join();

          thY1.interrupt();
          thY2.interrupt();
          thY3.interrupt();
          thY4.interrupt();
          thY5.interrupt();
          thY6.interrupt();
          thY7.interrupt();
          thY8.interrupt();
          thY9.interrupt();
          thY10.interrupt();
          thY11.interrupt();
          thY12.interrupt();

          break;
      default:
          break;
  }
} catch (InterruptedException e) {
  e.printStackTrace();
}
