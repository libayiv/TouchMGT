package com.security.modules.touch.service;

import com.security.modules.touch.entity.BFBusCenter;

public interface BFBusCenterService {

	BFBusCenter getInfo(String type);

	void update(BFBusCenter busCenter);

}
