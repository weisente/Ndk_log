//
// Created by san on 2019/2/19.
//

#ifndef NDK_FILEFLUSH_H
#define NDK_FILEFLUSH_H

#include <sys/types.h>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <unistd.h>
#include "FlushBuffer.h"

class AsyncFileFlush {

public:
    AsyncFileFlush();
    ~AsyncFileFlush();
    bool async_flush(FlushBuffer* flushBuffer);
    void stopFlush();

private:
    void async_log_thread();
    ssize_t flush(FlushBuffer* flushBuffer);

    bool exit = false;
    std::vector<FlushBuffer*> async_buffer;
    std::thread async_thread;
    std::condition_variable async_condition;
    std::mutex async_mtx;
};


#endif //LOG4A_FILEFLUSH_H
