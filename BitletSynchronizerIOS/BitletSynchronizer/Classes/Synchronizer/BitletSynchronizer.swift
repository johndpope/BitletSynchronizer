//
//  BitletSynchronizer.swift
//  Bitlet Synchronizer Pod
//
//  Library: synchronizes bitlets
//  A singleton to access and handle bitlets easily
//

public class BitletSynchronizer {
    
    // --
    // MARK: Singleton
    // --

    public static let shared = BitletSynchronizer()

    
    // --
    // MARK: Members
    // --
    
    public var cache = BitletMemoryCache()
    

    // --
    // MARK: Loading
    // --

    public func loadBitlet<Handler: BitletHandler>(_ bitletHandler: Handler, cacheKey: String? = nil, forced: Bool = false, success: @escaping ((_ bitlet: Handler.BitletData) -> Void), failure: @escaping ((_ error: Error) -> Void)) {
        if let cacheKey = cacheKey {
            cache.createEntryIfNeeded(forKey: cacheKey, handler: bitletHandler)
            if let entry = cache.getEntry(forKey: cacheKey) {
                entry.load(forced: forced, observer: BitletResultObserver<Handler.BitletData>(success: { data in
                    success(data)
                }, failure: { error in
                    failure(error)
                }))
            } else {
                failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey : "Unknown caching error"]))
            }
        } else {
            bitletHandler.load(observer: BitletResultObserver<Handler.BitletData>(success: { data in
                success(data)
            }, failure: { error in
                failure(error)
            }))
        }
    }
    
    public func loadBitlet<Handler: BitletHandler>(_ bitletHandler: Handler, cacheKey: String? = nil, forced: Bool = false, completion: @escaping ((_ bitlet: Handler.BitletData?, _ error: Error?) -> Void)) {
        if let cacheKey = cacheKey {
            cache.createEntryIfNeeded(forKey: cacheKey, handler: bitletHandler)
            if let entry = cache.getEntry(forKey: cacheKey) {
                entry.load(forced: forced, observer: BitletResultObserver<Handler.BitletData>(completion: { data, error in
                    completion(data, error)
                }))
            } else {
                completion(nil, NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey : "Unknown caching error"]))
            }
        } else {
            bitletHandler.load(observer: BitletResultObserver<Handler.BitletData>(completion: { data, error in
                completion(data, error)
            }))
        }
    }

    
    // --
    // MARK: Cache control
    // --
    
    public func cachedBitlet(forKey: String) -> Any? {
        return cache.getEntry(forKey: forKey)?.bitletData
    }
    
    public func cacheState(forKey: String) -> BitletCacheState {
        return cache.getEntry(forKey: forKey)?.state ?? .unavailable
    }
    
    public func clearCache(filter: String = "*", recursive: Bool = true) {
        cache.clear(filter: filter, recursive: recursive)
    }

}
