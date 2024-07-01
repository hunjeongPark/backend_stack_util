public List<AirUnitPricePerPersonListRs> airUnitPriceThreadSelectList(AirUnitPricePerPersonReqVo param, int plusMonth) {
    List<AirUnitPricePerPersonListRs> resList;
    try {
        //Multi CompletableFutre Join - 멀티 쓰레드로 동시에 셀렉트 시도
        CompletableFuture<AirUnitPricePerPersonListRs> selectData = CompletableFuture.supplyAsync(() -> modelMapper.map(bizStatisticsStMapper.SELECT_AIRLINE_UNITPRICEPERPERSON_LIST(searchRq)
                , new TypeToken<AirUnitPricePerPersonListRs>(){
                }.getType()));
    
        CompletableFuture<AirUnitPricePerPersonListRs> baseFirstData = CompletableFuture.supplyAsync(() -> modelMapper.map(bizStatisticsStMapper.SELECT_AIRLINE_UNITPRICEPERPERSON_LIST(baseFirstSearchRq)
                , new TypeToken<AirUnitPricePerPersonListRs>(){
                }.getType()));
    
        CompletableFuture<AirUnitPricePerPersonListRs> baseSecondData = new CompletableFuture<>();
        if (!searchRq.getStdrYearSecond().isEmpty()) {
            baseSecondData = CompletableFuture.supplyAsync(() -> modelMapper.map(bizStatisticsStMapper.SELECT_AIRLINE_UNITPRICEPERPERSON_LIST(baseSecondSearchRq)
                    , new TypeToken<AirUnitPricePerPersonListRs>() {
                    }.getType()));
        }
    
        List<CompletableFuture<AirUnitPricePerPersonListRs>> futures = Arrays.asList(selectData, baseFirstData, baseSecondData);
        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
    
        CompletableFuture<List<AirUnitPricePerPersonListRs>> results = CompletableFuture.allOf(futuresArray)
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    
        resList = results.get();
    } catch (ExecutionException | InterruptedException e) {
        throw new NotificationRuntimeVgtException("조회중 오류발생");
    }
    return resList;
}
