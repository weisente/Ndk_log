//
// Created by san on 2019/2/20.
//

#include <ctime>
#include "LogBufferHeader.h"

using namespace log_header;

//struct Header {  结构体 便于观察
//    char magic;
//    size_t log_len;
//    size_t log_path_len;
//    char* log_path;
//    char isCompress;
//};

LogBufferHeader::LogBufferHeader(void *data, size_t size) : data_ptr((char *) data), data_size(size) {
}

LogBufferHeader::~LogBufferHeader() {
}

void *LogBufferHeader::originPtr() {
    return data_ptr;
}

Header* LogBufferHeader::getHeader() {
    Header* header = new Header();
    if (isAvailable()) {
        header->magic = kMagicHeader;
        size_t log_len = 0;
        memcpy(&log_len, data_ptr + sizeof(char), sizeof(size_t));
        header->log_len = log_len;
        size_t log_path_len = 0;
        memcpy(&log_path_len, data_ptr + sizeof(char) + sizeof(size_t), sizeof(size_t));
        header->log_path_len = log_path_len;
        char *log_path = new char[log_path_len + 1];
        memset(log_path, 0, log_path_len + 1);
        memcpy(log_path, data_ptr + sizeof(char) + sizeof(size_t) + sizeof(size_t), log_path_len);
        header->log_path = log_path;
        char isCompress = (data_ptr + sizeof(char) + sizeof(size_t) + sizeof(size_t) + log_path_len)[0];
        header->isCompress = isCompress == 1;
    }
    return header;
}

size_t LogBufferHeader::getHeaderLen() {
    if (isAvailable()) {
        size_t log_path_len = 0;
        memcpy(&log_path_len, data_ptr + sizeof(char) + sizeof(size_t), sizeof(size_t));
        return calculateHeaderLen(log_path_len);
    }
    return 0;
}

void *LogBufferHeader::ptr() {
    return data_ptr + getHeaderLen();
}

void *LogBufferHeader::write_ptr() {
    return data_ptr + getHeaderLen() + getLogLen();
}


bool LogBufferHeader::isAvailable() {
    return data_ptr[0] == kMagicHeader;
}