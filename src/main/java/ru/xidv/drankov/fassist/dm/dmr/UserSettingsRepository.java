package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.xidv.drankov.fassist.dm.dao.UserSettings;

public  interface UserSettingsRepository extends JpaRepository<UserSettings,Long> {
}
