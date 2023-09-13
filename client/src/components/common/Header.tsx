'use client';

import Image from 'next/image';
import { useState } from 'react';

import useUserStore from '@/stores/userStore';

import useClient from '@/hooks/useClient';

import Logo from './Logo';
import HeaderLink from './HeaderLink';
import HeaderNav from './HeaderNav';

export default function Header() {
  const [isHover, setIsHover] = useState(false);
  const { isLogin, isGoogleLogin, profileImageUrl } = useUserStore();
  const isClient = useClient();

  const profileImage = () => {
    if (!profileImageUrl) return '/assets/img/bg_default_profile.png';

    if (isLogin || isGoogleLogin) {
      return profileImageUrl as string;
    }

    return '/assets/img/bg_default_profile.png';
  };

  // 1. 문제인식을 나는 어떻게 했는가? 2.문제를 해결하기 위한 삽질과정 3.결과 4. 느낀점

  return (
    <>
      <header
        className="
        fixed
        top-0
        flex
        justify-between
        items-center
        bg-[url('/assets/img/bg_wood_yellow.png')] 
        bg-contain 
        min-w-[480px] 
        w-full 
        h-[60px] 
        px-3
        pt-[1px]
        border-b-[8px] 
        border-border-10
        shadow-outer/down 
        z-10
        ">
        <Logo size="small" />
        <ul className="flex gap-2">
          <li>
            <HeaderLink
              location="/garden/1"
              content="activity"
              title="garden"
            />
          </li>
          <li>
            <HeaderLink
              location="/board"
              content="activity"
              title="community"
            />
          </li>
          <li>
            <HeaderLink
              location="/leafs/1"
              content="activity"
              title="leafCard"
            />
          </li>
          {isClient && (isLogin || isGoogleLogin) ? ( // 서버에 있을때 다르게 동작
            <li
              onMouseOver={() => setIsHover(true)}
              onMouseLeave={() => setIsHover(false)}>
              <Image
                src={profileImage()}
                alt="profile_img"
                className={`rounded-[50%] border-brown-50 border-[3px] w-11 h-11 cursor-pointer `}
                width={44}
                height={44}
              />
              {isHover && (isLogin || isGoogleLogin) && (
                <div className="flex justify-end">
                  <HeaderNav isHover={isHover} />
                </div>
              )}
            </li>
          ) : (
            <li>
              <HeaderLink location="/signin" content="auth" title="signin" />
            </li>
          )}
        </ul>
      </header>
    </>
  );
}