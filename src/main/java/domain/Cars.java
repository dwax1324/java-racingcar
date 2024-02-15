package domain;

import dto.CarNameRequest;
import java.util.ArrayList;
import java.util.List;

public class Cars {
    public static final int MIN_CAR_COUNT = 2;
    public static final int MAX_CAR_COUNT = 50;
    private final List<Car> cars;

    public Cars(List<Car> cars) {
        validate(cars);
        this.cars = cars;
    }

    public static Cars from(List<Car> cars) {
        return new Cars(cars);
    }

    private void validate(List<Car> cars) {
        if (!(cars.size() >= MIN_CAR_COUNT && cars.size() <= MAX_CAR_COUNT)) {
            throw new IllegalArgumentException("자동차는 2대에서 50대 사이로 입력해주세요.");
        }
    }

    public static Cars fromEmpty() {
        return new Cars(List.of(Car.fromEmpty(), Car.fromEmpty()));
    }

    public static Cars fromDto(CarNameRequest carsNameRequest) {
        List<Car> carsTmp = new ArrayList<>();
        for (String carName : carsNameRequest.list()) {
            carsTmp.add(Car.fromName(carName));
        }
        return new Cars(carsTmp);
    }

    public void move(RandomMovementGenerator randomMovementGenerator) {
        for (Car car : cars) {
            moveCar(randomMovementGenerator, car);
        }
    }

    private static void moveCar(RandomMovementGenerator randomMovementGenerator, Car car) {
        if (randomMovementGenerator.generate()) {
            car.move();
        }
    }

    public List<Car> getMaxDistanceCars() {
        List<Car> maxDistanceCars = new ArrayList<>();
        Car maxDistanceCar = cars.stream()
                .max(Car::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("최댓값 계산에 오류가 발생했습니다."));

        for (Car car : cars) {
            addWinners(car, maxDistanceCar, maxDistanceCars);
        }
        return maxDistanceCars;
    }

    private static void addWinners(Car car, Car maxDistanceCar, List<Car> winners) {
        if (car.isSameDistance(maxDistanceCar)) {  //TODO: 메서드 분리 더 잘하기
            winners.add(car);
        }
    }

    public List<Car> getCars() {
        return cars;
    }

    public void add(Car car) {

    }
}