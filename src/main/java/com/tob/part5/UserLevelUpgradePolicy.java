package com.tob.part5;

import com.tob.part5.vo.User;

/**
 * TODO : 유저 레벨 업그레이드 정책에 따라 구현체를 DI하기
 */
public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);

    void upgradeLevel(User user);
}
